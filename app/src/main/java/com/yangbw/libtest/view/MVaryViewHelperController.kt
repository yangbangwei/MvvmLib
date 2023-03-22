package com.yangbw.libtest.view

import android.view.View
import android.widget.Button
import android.widget.TextView
import com.library.common.view.IVaryViewHelperController
import com.library.common.view.VaryViewHelper
import com.yangbw.libtest.R

/**
 * 菜单定义加载效果
 *
 * @author :yangbw
 * @date :2020/9/15
 */
class MVaryViewHelperController private constructor(private val helper: VaryViewHelper) :
    IVaryViewHelperController {

    //是否已经调用过restore方法
    private var hasRestore: Boolean = false

    constructor(replaceView: View) : this(VaryViewHelper(replaceView))


    override fun showLoading() {
        showLoading(null)
    }

    override fun showLoading(msg: String?) {
        hasRestore = false
        val layout = helper.inflate(R.layout.layout_page_menu_load)
        msg?.let {
            val tvMsg = layout.findViewById<TextView>(R.id.tv_msg)
            tvMsg.text = it
        }
        helper.showView(layout)
    }

    override fun showError(listener: View.OnClickListener?) {
        showError(null, listener)
    }

    override fun showError(
        msg: String?,
        listener: View.OnClickListener?
    ) {
        hasRestore = false
        val layout = helper.inflate(R.layout.layout_page_menu_error)
        val againBtn = layout.findViewById<Button>(R.id.btn_reload)
        val tvMsg = layout.findViewById<TextView>(R.id.tv_msg)
        msg?.let {
            tvMsg.text = it
        }
        if (null != listener) {
            againBtn.setOnClickListener(listener)
        }
        helper.showView(layout)
    }

    override fun showEmpty(listener: View.OnClickListener?) {
    }

    override fun showEmpty(emptyMsg: String?, listener: View.OnClickListener?) {
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