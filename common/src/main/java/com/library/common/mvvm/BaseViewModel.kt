package com.library.common.mvvm

import android.view.View
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.NetworkUtils
import com.library.common.R
import com.library.common.base.BaseApplication
import com.library.common.config.AppConfig
import com.library.common.em.RequestDisplay
import com.library.common.http.exception.ReturnCodeException
import com.library.common.http.exception.ReturnCodeNullException
import com.library.common.http.interceptor.IReturnCodeErrorInterceptor
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.reflect.ParameterizedType

/**
 * BaseViewModel封装
 *
 * @author yangbw
 * @date 2020/8/31
 */
@Suppress("UNCHECKED_CAST")
abstract class BaseViewModel<API> : ViewModel(), LifecycleObserver {

    //接口类
    private var apiService: API? = null

    //默认相关错误提示
    protected val emptyMsg: String by lazy { BaseApplication.context.getString(R.string.no_data) }
    protected val errorMsg: String by lazy { BaseApplication.context.getString(R.string.network_error) }
    protected val codeNullMsg: String by lazy { BaseApplication.context.getString(R.string.no_suc_code) }

    /**
     * 重试的监听
     */
    var listener: View.OnClickListener? = null

    /**
     * 视图变化
     */
    val viewState: ViewState by lazy { ViewState() }

    /**
     * 获取接口操作类
     */
    fun getApiService(): API {
        if (apiService == null) {
            apiService = AppConfig.getRetrofit().create(
                (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<API>
            )
        }
        return apiService ?: throw RuntimeException("Api service is null")
    }

    /**
     * 开始执行方法
     */
    protected abstract fun onStart()

    /**
     * 所有网络请求都在 viewModelScope 域中启动，当页面销毁时会自动
     * 调用ViewModel的  #onCleared 方法取消所有协程
     */
    protected fun launchUI(block: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch { block() }

    /**
     * 用流的方式进行网络请求
     */
    fun <T> launchFlow(block: suspend () -> T): Flow<T> {
        return flow {
            emit(block())
        }
    }

    /**
     * 过滤请求结果，其他全抛异常
     * @param block 请求体
     * @param success 成功回调
     * @param error 失败回调
     * @param complete  完成回调（无论成功失败都会调用）
     * @param type RequestDisplay类型 NULL无交互  TOAST  REPLACE 替换
     * @param msg TOAST文字提醒
     *
     **/
    fun <T> launchOnlyResult(
        block: suspend CoroutineScope.() -> IRes<T>,
        //成功
        success: (T?) -> Unit = {},
        //错误 根据错误进行不同分类
        error: (Throwable) -> Unit = {},
        //完成
        complete: () -> Unit = {},
        //重试
        reTry: () -> Unit = {},
        //当前请求的CurrentDomainName,默认的DOMAIN_NAME，也可自行设置
        currentDomainName: String = AppConfig.DOMAIN_NAME,
        //接口操作交互类型
        type: RequestDisplay = RequestDisplay.REPLACE,
        //弹窗文字提醒
        msg: String = ""
    ) {
        //开始请求接口前
        when (type) {
            RequestDisplay.NULL -> {
            }
            RequestDisplay.TOAST -> {
                viewState.showDialogProgress.value = msg
            }
            RequestDisplay.REPLACE -> {
                viewState.showLoading.call()
            }
        }
        //正式请求接口
        launchUI {
            //异常处理
            handleException(
                //调用接口
                { withContext(Dispatchers.IO) { block() } },
                { res ->
                    //接口成功返回
                    executeResponse(res, currentDomainName) {
                        success(it)
                    }
                },
                {
                    //通用异常处理
                    if (!NetworkUtils.isConnected()) {
                        onNetWorkError(type) { reTry() }
                    } else {
                        when (it) {
                            is ReturnCodeException -> {
                                isIntercepted(it)
                                onReturnCodeError(type, it.message) { reTry() }
                            }
                            is ReturnCodeNullException -> {
                                onNetWorkError(type, codeNullMsg) { reTry() }
                            }
                            else -> {
                                //UnknownHostException 1：服务器地址错误；2：网络未连接
                                onNetWorkError(type) { reTry() }
                            }
                        }
                    }
                    //自定义异常处理
                    error(it)
                },
                {
                    //接口完成
                    complete()
                }
            )
        }
    }

    /**
     * 请求结果过滤
     */
    private suspend fun <T> executeResponse(
        response: IRes<T>,
        currentDomainName: String,
        success: suspend CoroutineScope.(T?) -> Unit
    ) {
        coroutineScope {
            //判断是否设置状态码
            if (AppConfig.getMoreBaseUrl() && currentDomainName != AppConfig.DOMAIN_NAME) {
                val retSuccessList = AppConfig.getRetSuccessMap()?.get(currentDomainName)
                if (retSuccessList == null || retSuccessList.isEmpty()) {
                    ///抛出未设置状态码异常
                    throw ReturnCodeNullException(response.getBaseCode(), response.getBaseMsg())
                }
                //判断状态码是否包含
                if (!retSuccessList.contains(response.getBaseCode())) {
                    //抛出状态码错误异常
                    throw ReturnCodeException(response.getBaseCode(), response.getBaseMsg())
                }
            } else {
                val retSuccessList = AppConfig.getRetSuccess() ?: throw ReturnCodeNullException(
                    response.getBaseCode(),
                    response.getBaseMsg()
                )
                //判断状态码是否包含
                if (!retSuccessList.equals(response.getBaseCode())) {
                    //抛出状态码错误异常
                    throw ReturnCodeException(response.getBaseCode(), response.getBaseMsg())
                }
            }
            //数据正常，异常。都在success中处理
            success(response.getBaseResult())
            //完成的回调所有弹窗消失
            viewState.dismissDialog.call()
            viewState.restore.call()
        }
    }

    /**
     * 异常统一处理
     */
    protected suspend fun <T> handleException(
        block: suspend CoroutineScope.() -> IRes<T>,
        success: suspend CoroutineScope.(IRes<T>) -> Unit,
        error: suspend CoroutineScope.(Throwable) -> Unit,
        complete: suspend CoroutineScope.() -> Unit
    ) {
        coroutineScope {
            try {
                success(block())
            } catch (e: Throwable) {
                error(e)
            } finally {
                complete()
            }
        }
    }

    /**
     * 网络异常，状态码异常，未设置成功状态码
     */
    private fun onNetWorkError(
        type: RequestDisplay,
        errorMsg: String = this.errorMsg,
        reTry: () -> Unit = {}
    ) {
        when (type) {
            RequestDisplay.NULL -> {
            }
            RequestDisplay.TOAST -> {
                viewState.showToast.value = errorMsg
                viewState.dismissDialog.call()
            }
            RequestDisplay.REPLACE -> {
                this.listener = View.OnClickListener {
                    reTry()
                }
                viewState.showNetworkError.value = errorMsg
            }
        }
    }

    /**
     * 返回code错误
     */
    protected fun onReturnCodeError(
        type: RequestDisplay,
        message: String?,
        reTry: () -> Unit = {}
    ) {
        when (type) {
            RequestDisplay.NULL -> {
            }
            RequestDisplay.TOAST -> {
                viewState.showToast.value = message
                viewState.dismissDialog.call()
            }
            RequestDisplay.REPLACE -> {
                this.listener = View.OnClickListener {
                    reTry()
                }
                viewState.showNetworkError.call()
            }
        }
    }

    /**
     * 判断是否被拦截
     */
    protected fun isIntercepted(t: Throwable): Boolean {
        //是否被拦截了
        var isIntercepted = false
        for (interceptor: IReturnCodeErrorInterceptor in AppConfig.getRetCodeInterceptors()) {
            if (interceptor.intercept((t as ReturnCodeException).returnCode)) {
                isIntercepted = true
                interceptor.doWork(t.returnCode, t.message)
                break
            }
        }
        return isIntercepted
    }

}