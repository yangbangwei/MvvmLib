package com.library.common.view

import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.library.common.R
import com.library.common.base.BaseApplication

/**
 * 变化view的默认辅助控制类
 *
 * @author yangbw
 * @date 2020/8/31
 */
class VaryViewHelperController private constructor(private val helper: VaryViewHelper) :
    IVaryViewHelperController {

    //是否已经调用过restore方法
    private var hasRestore: Boolean = false

    constructor(replaceView: View) : this(
        VaryViewHelper(
            replaceView
        )
    )

    override fun showLoading() {
        hasRestore = false
        val layout = helper.inflate(R.layout.page_loading)
        helper.showView(layout)
    }

    override fun showLoading(msg: String?) {
        hasRestore = false
        val layout = helper.inflate(R.layout.page_loading)
        val tvMsg = layout.findViewById<TextView>(R.id.tv_msg)
        tvMsg.text = msg
        helper.showView(layout)
    }

    override fun showEmpty(listener: View.OnClickListener?) {
        showEmpty(null, listener)
    }

    override fun showEmpty(emptyMsg: String?, listener: View.OnClickListener?) {
        hasRestore = false
        val layout = helper.inflate(R.layout.page_empty)
        val againBtn = layout.findViewById<Button>(R.id.btn_reload)
        val textView = layout.findViewById<TextView>(R.id.tv_empty)
        if (!TextUtils.isEmpty(emptyMsg)) {
            textView.text = emptyMsg
        }
        if (null != listener) {
            againBtn.setOnClickListener(listener)
            againBtn.visibility = View.VISIBLE
        } else {
            againBtn.visibility = View.GONE
        }
        helper.showView(layout)
    }

    override fun showNetworkError(listener: View.OnClickListener?) {
        showNetworkError(BaseApplication.context.getString(R.string.network_error), listener)
    }

    override fun showNetworkError(
        msg: String?,
        listener: View.OnClickListener?
    ) {
        hasRestore = false
        val layout = helper.inflate(R.layout.page_error)
        val againBtn =
            layout.findViewById<Button>(R.id.btn_reload)
        val tvMsg = layout.findViewById<TextView>(R.id.tv_msg)
        tvMsg.text = msg
        if (null != listener) {
            againBtn.setOnClickListener(listener)
            againBtn.visibility = View.VISIBLE
        } else {
            againBtn.visibility = View.GONE
        }
        helper.showView(layout)
    }

    override fun showCustomView(
        drawableInt: Int,
        title: String?,
        msg: String?,
        btnText: String?,
        listener: View.OnClickListener?
    ) {
    }

    override fun restore() {
        hasRestore = true
        helper.restoreView()
    }

    override val isHasRestore: Boolean
        get() = hasRestore

}