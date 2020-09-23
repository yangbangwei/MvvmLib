package com.library.common.mvvm

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.NetworkUtils
import com.library.common.config.AppConfig
import com.library.common.em.RequestDisplay
import com.library.common.http.exception.ResultException
import com.library.common.http.exception.ReturnCodeException
import com.library.common.http.exception.ReturnCodeNullException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

/**
 * BaseListViewModel封装
 *
 * @author yangbw
 * @date 2020/8/31
 */
abstract class BaseListViewModel<API> : BaseViewModel<API>() {

    /**
     * 对于list的不同展示
     */
    var mListData: MutableLiveData<*>? = null

    /**
     * 过滤请求结果，其他全抛异常
     * @param block 请求体
     * @param success 成功回调
     * @param error 失败回调
     * @param complete  完成回调（无论成功失败都会调用）
     * @param type RequestDisplay类型 NULL无交互  TOAST  REPLACE 替换
     *
     **/
    fun <T> launchOnlyResult(
        block: suspend CoroutineScope.() -> IRes<T>,
        //成功
        success: (IRes<T>) -> Unit = {
        },
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
        //分页数,-1为非list接口
        pageNo: Int = -1,
        //加载弹窗提醒内容
        msg: String = ""
    ) {
        //开始请求接口前
        if (pageNo == 1) {
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
        }
        //正式请求接口
        launchUI {
            //异常处理
            handleException(
                //调用接口
                { withContext(Dispatchers.IO) { block() } },
                { res ->
                    //接口成功返回
                    executeResponse(pageNo, res, currentDomainName) {
                        //自定义成功处理
                        success(it)
                    }
                },
                {
                    //通用异常处理
                    if (!NetworkUtils.isConnected()) {
                        onError(type, pageNo) { reTry() }
                    } else {
                        when (it) {
                            is ReturnCodeNullException -> {
                                onError(type, pageNo, codeNullMsg) { reTry() }
                            }
                            is ReturnCodeException -> {
                                isIntercepted(it)
                                onError(type, pageNo, it.message) { reTry() }
                            }
                            is ResultException -> {
                                onEmpty { reTry() }
                            }
                            else -> {
                                //服务异常 1：服务器地址错误；2：网络未连接
                                onError(type, pageNo, serverErrorMsg) { reTry() }
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
        pageNo: Int,
        response: IRes<T>,
        currentDomainName: String,
        success: suspend CoroutineScope.(IRes<T>) -> Unit
    ) {
        coroutineScope {
            //单一地址和多地址判断
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
                if (!retSuccessList.contains(response.getBaseCode())) {
                    //抛出状态码错误异常
                    throw ReturnCodeException(response.getBaseCode(), response.getBaseMsg())
                }
            }
            //成功处理，判断是否为列表数据接口
            if (pageNo != -1) {
                //数据为空的情况
                if (response.getBaseResult() == null
                    || response.getBaseResult().toString() == "[]"
                ) {
                    //是否为第一页
                    if (pageNo == 1) {
                        throw ResultException(response.getBaseMsg())
                    } else {
                        viewState.showEmpty.call()
                    }
                } else {
                    //列表接口数据赋值
                    mListData?.value = response.getBaseResult()
                    //返回列表数据
                    success(response)
                    //完成的回调页面效果处理
                    if (pageNo == 1) {
                        viewState.restore.call()
                    } else {
                        viewState.refreshComplete.call()
                    }
                }
            } else {
                //无需判断数据是否为空,直接返回处理
                success(response)
                //完成的回调页面效果处理
                viewState.dismissDialogProgress.call()
            }

        }
    }

    /**
     * list数据为空的情况
     */
    private fun onEmpty(reTry: () -> Unit = {}) {
        viewState.showEmpty.value = emptyMsg
        this.listener = View.OnClickListener {
            reTry()
        }
    }

    /**
     * 网络异常，状态码异常，未设置成功状态码
     */
    private fun onError(
        type: RequestDisplay,
        pageNo: Int,
        msg: String? = this.errorMsg,
        reTry: () -> Unit = {}
    ) {
        when (type) {
            RequestDisplay.NULL -> {
            }
            RequestDisplay.TOAST -> {
                viewState.showToast.value = msg
                viewState.dismissDialogProgress.call()
            }
            RequestDisplay.REPLACE -> {
                if (pageNo == 1) {
                    this.listener = View.OnClickListener {
                        reTry()
                    }
                    viewState.showError.value = msg
                } else {
                    viewState.refreshComplete.call()
                    viewState.restore.call()
                    viewState.showTips.value = msg
                }
            }
        }
    }
}