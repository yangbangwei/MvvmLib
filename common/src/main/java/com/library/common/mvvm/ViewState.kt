package com.library.common.mvvm

/**
 * 界面UI显示处理
 *
 * @author yangbw
 * @date 2020/9/1
 */
class ViewState {

    /**
     *  显示Loading弹窗
     */
    val showDialogProgress by lazy { SingleLiveEvent<String>() }

    /**
     *  隐藏Loading弹窗
     */
    val dismissDialogProgress by lazy { SingleLiveEvent<String>() }

    /**
     * 吐司显示内容
     */
    val showToast by lazy { SingleLiveEvent<String>() }

    /**
     * Tip提醒
     */
    val showTips by lazy { SingleLiveEvent<String>() }

    /**
     * 页面loading加载效果
     */
    val showLoading by lazy { SingleLiveEvent<String>() }

    /**
     * 数据为空
     */
    val showEmpty by lazy { SingleLiveEvent<String>() }

    /**
     * 网络异常
     */
    val showError by lazy { SingleLiveEvent<String>() }

    /**
     * 恢复初始效果
     */
    val restore by lazy { SingleLiveEvent<Void>() }

    /**
     * 刷新结束
     */
    val refreshComplete by lazy { SingleLiveEvent<Void>() }
}