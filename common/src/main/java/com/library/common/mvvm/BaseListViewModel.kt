package com.library.common.mvvm

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.NetworkUtils
import com.library.common.config.AppConfig
import com.library.common.em.RequestDisplay
import com.library.common.http.exception.NetWorkException
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
    var mResult: MutableLiveData<*>? = null

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
        success: (T) -> Unit = {
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
                    executeResponse(pageNo, res, currentDomainName) { success(it) }
                },
                {
                    //通用异常处理
                    if (!NetworkUtils.isConnected()) {
                        onNetWorkError(pageNo, type) { reTry() }
                    } else {
                        when (it) {
                            is ReturnCodeException -> {
                                isIntercepted(it)
                                onReturnCodeError(type, it.message) { reTry() }
                            }
                            is ReturnCodeNullException -> {
                                onNetWorkError(pageNo, type, codeNullMsg) { reTry() }
                            }
                            is ResultException -> {
                                onTEmpty(pageNo, type) { reTry() }
                            }
                            else -> {
                                //UnknownHostException 1：服务器地址错误；2：网络未连接
                                onNetWorkError(pageNo, type) { reTry() }
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
        success: suspend CoroutineScope.(T) -> Unit
    ) {
        coroutineScope {
            //多baseUrl
            if (AppConfig.getMoreBaseUrl() && currentDomainName != AppConfig.DOMAIN_NAME) {
                //获取当前baseUrl对应的成功码
                val retSuccessList = AppConfig.getRetSuccessMap()?.get(currentDomainName)
                //当前对应的baseUrl对应的code
                if (retSuccessList != null) {
                    //状态码正确
                    if (retSuccessList.contains(response.getBaseCode())) {
                        //数据为空，或者list.size=0
                        if (response.getBaseResult() == null
                            || response.getBaseResult().toString() == "[]"
                        ) {
                            //完成的回调所有弹窗消失
                            if (pageNo == 1) {
                                //返回结果null
                                throw ResultException(response.getBaseMsg())
                            } else {
                                viewState.showEmpty.call()
                            }
                        } else {
                            //列表接口数据赋值
                            if (pageNo != -1) {
                                mResult?.value = response.getBaseResult()
                            }
                            success(response.getBaseResult())
                            if (pageNo == 1) {
                                //完成的回调所有弹窗消失
                                viewState.dismissDialog.call()
                                viewState.restore.call()
                            } else {
                                viewState.refreshComplete.call()
                            }
                        }
                    } else {
                        //状态码错误
                        throw ReturnCodeException(response.getBaseCode(), response.getBaseMsg())
                    }
                } else {
                    //未设置状态码
                    throw ReturnCodeNullException(response.getBaseCode(), response.getBaseMsg())
                }
                //接口多状态码的返回 接口成功返回后判断是否是增删改查成功，不满足的话，返回异常
            } else if (AppConfig.getRetSuccessList().isNotEmpty()) {
                //成功
                if (AppConfig.getRetSuccessList().contains(response.getBaseCode())) {
                    //数据为空，或者list.size=0
                    if (response.getBaseResult() == null
                        || response.getBaseResult().toString() == "[]"
                    ) {
                        //完成的回调所有弹窗消失
                        if (pageNo == 1) {
                            //返回结果null
                            throw ResultException(response.getBaseMsg())
                        } else {
                            viewState.showEmpty.call()
                        }
                    } else {
                        //列表接口数据赋值
                        if (pageNo != -1) {
                            mResult?.value = response.getBaseResult()
                        }
                        success(response.getBaseResult())
                        if (pageNo == 1) {
                            //完成的回调所有弹窗消失
                            viewState.dismissDialog.call()
                            viewState.restore.call()
                        } else {
                            viewState.refreshComplete.call()
                        }
                    }
                } else {
                    //状态码错误
                    throw ReturnCodeException(response.getBaseCode(), response.getBaseMsg())
                }
                //接口单状态码
            } else if (AppConfig.getRetSuccess() != null) {
                //成功
                if (response.getBaseCode() == AppConfig.getRetSuccess()) {
                    if (response.getBaseResult() == null
                        || response.getBaseResult().toString() == "[]"
                    ) {
                        //完成的回调所有弹窗消失
                        if (pageNo == 1) {
                            //返回结果null
                            throw ResultException(response.getBaseMsg())
                        } else {
                            viewState.showEmpty.call()
                        }
                    } else {
                        //列表接口数据赋值
                        if (pageNo != -1) {
                            mResult?.value = response.getBaseResult()
                        }
                        success(response.getBaseResult())
                        if (pageNo == 1) {
                            //完成的回调所有弹窗消失
                            viewState.dismissDialog.call()
                            viewState.restore.call()
                        } else {
                            viewState.refreshComplete.call()
                        }
                    }
                } else {
                    //状态码错误
                    throw ReturnCodeException(response.getBaseCode(), response.getBaseMsg())
                }
            } else {
                //未设置状态码
                throw ReturnCodeNullException(response.getBaseCode(), response.getBaseMsg())
            }
        }
    }

    /**
     * 数据为空
     */
    private fun onTEmpty( //重试
        pageNo: Int,
        type: RequestDisplay,
        reTry: () -> Unit = {
        }
    ) {
        if (pageNo == 1) {
            when (type) {
                RequestDisplay.NULL -> {
                }
                RequestDisplay.TOAST -> {
                    viewState.showToast.value = emptyMsg
                    viewState.dismissDialog.call()
                }
                RequestDisplay.REPLACE -> {
                    this.listener = View.OnClickListener {
                        reTry()
                    }
                    viewState.showEmpty.value = emptyMsg

                }
            }
        }
    }

    /**
     * 网络异常，状态码异常，未设置成功状态码
     */
    private fun onNetWorkError(
        pageNo: Int,
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
                if (pageNo == 1) {
                    this.listener = View.OnClickListener {
                        reTry()
                    }
                    viewState.showNetworkError.value = errorMsg
                } else {
                    viewState.refreshComplete.call()
                    viewState.restore.call()
                    viewState.showTips.value = errorMsg
                }
            }
        }
    }
}