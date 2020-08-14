package com.library.common.view

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import com.library.common.R

/**
 * 弹出浮动加载进度条
 */
class LoadingDialog(private val mContext: Context) {
    /**
     * 加载数据对话框
     */
    private var mLoadingDialog: Dialog? = null
    private var mIvLoad: ImageView? = null

    fun show() {
        show(null, false)
    }

    /**
     * 显示加载对话框
     *
     * @param msg        对话框显示内容
     * @param cancelable 对话框是否可以取消
     */
    fun show(msg: String?, cancelable: Boolean) {
        val view = LayoutInflater.from(mContext).inflate(R.layout.dialog_loading, null)
        val loadingText = view.findViewById<View>(R.id.id_tv_loading_dialog_text) as TextView
        if (!TextUtils.isEmpty(msg)) {
            loadingText.text = msg
        }
        mLoadingDialog = Dialog(mContext, R.style.CustomProgressDialog)
        mLoadingDialog!!.setCancelable(cancelable)
        mLoadingDialog!!.setCanceledOnTouchOutside(false)
        mLoadingDialog!!.setContentView(view)
        if (null != mLoadingDialog
            && !(mContext as Activity).isFinishing
        ) {
            mLoadingDialog!!.show()
        }
    }

    /**
     * 判断是否显示
     *
     * @return
     */
    val isShowing: Boolean
        get() = mLoadingDialog!!.isShowing

    /**
     * 关闭对话框
     */
    fun cancel() {
        if (null != mLoadingDialog
            && mLoadingDialog!!.isShowing
        ) {
            mLoadingDialog!!.dismiss()
        }
    }

}