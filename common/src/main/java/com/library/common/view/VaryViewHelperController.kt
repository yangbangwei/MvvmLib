package com.library.common.view

import android.view.View
import android.widget.Button
import android.widget.TextView
import com.library.common.R

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
        showLoading(null)
    }

    override fun showLoading(msg: String?) {
        hasRestore = false
        val layout = helper.inflate(R.layout.page_loading)
        msg?.let {
            val tvMsg = layout.findViewById<TextView>(R.id.tv_msg)
            tvMsg.text = it
        }
        helper.showView(layout)
    }

    override fun showEmpty(listener: View.OnClickListener?) {
        showEmpty(null, listener)
    }

    override fun showEmpty(emptyMsg: String?, listener: View.OnClickListener?) {
        hasRestore = false
        val layout = helper.inflate(R.layout.page_empty)
        val btnReload = layout.findViewById<Button>(R.id.btn_reload)
        emptyMsg?.let {
            val tvMsg = layout.findViewById<TextView>(R.id.tv_msg)
            tvMsg.text = it
        }
        if (null != listener) {
            btnReload.setOnClickListener(listener)
            btnReload.visibility = View.VISIBLE
        } else {
            btnReload.visibility = View.GONE
        }
        helper.showView(layout)
    }

    override fun showNetworkError(listener: View.OnClickListener?) {
        showNetworkError(null, listener)
    }

    override fun showNetworkError(
        msg: String?,
        listener: View.OnClickListener?
    ) {
        hasRestore = false
        val layout = helper.inflate(R.layout.page_error)
        val btnReload = layout.findViewById<Button>(R.id.btn_reload)
        msg?.let {
            val tvMsg = layout.findViewById<TextView>(R.id.tv_msg)
            tvMsg.text = it
        }
        if (null != listener) {
            btnReload.setOnClickListener(listener)
            btnReload.visibility = View.VISIBLE
        } else {
            btnReload.visibility = View.GONE
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