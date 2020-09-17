package com.library.common.view

import android.app.Dialog
import android.content.Context
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.library.common.R

/**
 * 提醒弹窗
 *
 * @author :yangbw
 * @date :2020/9/17
 */
@Suppress("unused")
class CommonDialog(builder: Builder) {

    private var mContext: Context
    private var mTitle: String?
    private var mMessage: String?
    private var isHasBack = false
    private var mNegativeButtonText: CharSequence?
    private var mPositiveButtonText: CharSequence?
    private var mNegativeButtonListener: OnDialogClickListener?
    private var mPositiveButtonListener: OnDialogClickListener?

    private lateinit var mDialog: Dialog
    private lateinit var mTvTitle: TextView
    private lateinit var mTvMessage: TextView
    private lateinit var mTvLeft: TextView
    private lateinit var mTvRight: TextView
    private lateinit var mDiv: View

    init {
        mContext = builder.mContext
        mTitle = builder.mTitle
        mMessage = builder.mMessage
        isHasBack = builder.isHasBack
        mNegativeButtonText = builder.mNegativeButtonText
        mPositiveButtonText = builder.mPositiveButtonText
        mNegativeButtonListener = builder.mNegativeButtonListener
        mPositiveButtonListener = builder.mPositiveButtonListener
        initView()
    }

    /**
     * 初始化布局文件
     */
    private fun initView() {
        val rootView: View = View.inflate(mContext, R.layout.dialog_common, null)
        mTvTitle = rootView.findViewById(R.id.tv_title)
        mTvMessage = rootView.findViewById(R.id.tv_message)
        mTvLeft = rootView.findViewById(R.id.tv_left)
        mTvRight = rootView.findViewById(R.id.tv_right)
        mDiv = rootView.findViewById(R.id.div)
        // 定义Dialog布局和参数
        mDialog = Dialog(mContext, R.style.CustomDialog)
        mDialog.setContentView(rootView)
        mDialog.setCanceledOnTouchOutside(false)
        updateDialogUI()
        mDialog.show()
    }

    private fun updateDialogUI() {
        //标题
        if (hasNull(mTitle)) {
            mTvTitle.visibility = View.VISIBLE
            mTvTitle.text = mTitle
        }
        //提醒内容
        if (hasNull(mMessage)) {
            mTvMessage.text = mMessage
        }
        //左边提醒文字，点击事件
        if (hasNull(mNegativeButtonText)) {
            mTvLeft.visibility = View.VISIBLE
            mTvLeft.text = mNegativeButtonText
            mTvLeft.setOnClickListener {
                mDialog.dismiss()
                mNegativeButtonListener!!.onClick(mDialog, 0)
            }
        } else {
            mTvLeft.visibility = View.GONE
            mDiv.visibility = View.GONE
        }
        //右边提醒文字，点击事件
        if (hasNull(mPositiveButtonText)) {
            mTvRight.visibility = View.VISIBLE
            mTvRight.text = mPositiveButtonText
            mTvRight.setOnClickListener {
                mDialog.dismiss()
                mPositiveButtonListener?.onClick(mDialog, 1)
            }
        }
        //是否禁止返回按钮
        mDialog.setCancelable(isHasBack)
    }

    private fun hasNull(msg: CharSequence?): Boolean {
        return !TextUtils.isEmpty(msg)
    }

    class Builder(val mContext: Context) {
        var mTitle: String? = null
            private set
        var mMessage: String? = null
            private set
        var isHasBack = true
            private set
        var mNegativeButtonText: CharSequence? = null
            private set
        var mPositiveButtonText: CharSequence? = null
            private set
        var mNegativeButtonListener: OnDialogClickListener? = null
            private set
        var mPositiveButtonListener: OnDialogClickListener? = null
            private set

        fun setTitle(title: String?): Builder {
            mTitle = title
            return this
        }

        fun setMessage(message: String?): Builder {
            mMessage = message
            return this
        }

        fun setHasBack(isHasBack: Boolean): Builder {
            this.isHasBack = isHasBack
            return this
        }

        fun setNegativeButton(text: CharSequence?, listener: OnDialogClickListener?): Builder {
            mNegativeButtonText = text
            mNegativeButtonListener = listener
            return this
        }

        fun setPositiveButton(text: CharSequence?, listener: OnDialogClickListener?): Builder {
            mPositiveButtonText = text
            mPositiveButtonListener = listener
            return this
        }

        fun show(): CommonDialog {
            return CommonDialog(this)
        }
    }

    interface OnDialogClickListener {
        fun onClick(dialog: Dialog?, which: Int)
    }

}