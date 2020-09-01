package com.library.common.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.library.common.R

/**
 * 变化view辅助类用于增删view
 *
 * @author yangbw
 * @date 2020/8/31
 */
class VaryViewHelper(
    /**
     * 需替换的布局view
     * @return
     */
    //要替换的view 必须是ViewGroup的相关继承类
    override val replaceView: View
) : IVaryViewHelper {

    //替换view的夫view容器
    private var parentView: ViewGroup? = null
    //要替换的view的下角标
    private var viewIndex = 0
    //夫容器的布局参数
    private var params: ViewGroup.LayoutParams? = null
    //当前显示的view
    private var mCurrentView: View? = null

    /**
     * 当前显示的view
     * @return
     */
    override val currentView: View
        get() = mCurrentView!!


    /**
     * 恢复到需替换的布局view
     */
    override fun restoreView() {
        showView(replaceView)
    }

    /**
     * 设置要展示的view
     * @param view
     */
    override fun showView(view: View) {
        if (parentView == null) {
            init()
        }
        mCurrentView = view
        if (parentView?.getChildAt(viewIndex) != view) {
            if (view.parent != null) {
                val parent: ViewGroup = view.parent as ViewGroup
                parent.removeView(view)
            }
            parentView?.removeViewAt(viewIndex)
            parentView?.addView(view, viewIndex, params)
        }
    }

    /**
     * 设置要加载的布局layoutId
     * @param layoutId
     * @return
     */
    override fun inflate(layoutId: Int): View {
        return LayoutInflater.from(replaceView.context).inflate(layoutId, null, false)
    }

    /**
     * 上下文
     * @return
     */
    override val context: Context
        get() = replaceView.context

    private fun init() {
        params = replaceView.layoutParams
        parentView =
            if (replaceView.parent != null) {
                replaceView.parent as ViewGroup
            } else {
                replaceView.rootView.findViewById(R.id.content)
            }
        var count = parentView?.childCount
        if (count == null) {
            count = 0
        }
        for (index in 0 until count) {
            if (replaceView === parentView?.getChildAt(index)) {
                viewIndex = index
                break
            }
        }
        mCurrentView = replaceView
    }

}