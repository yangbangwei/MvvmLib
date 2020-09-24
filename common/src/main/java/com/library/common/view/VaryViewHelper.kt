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
class VaryViewHelper(override val replaceView: View) : IVaryViewHelper {

    /**
     * 替换view的父view容器
     */
    private var mParentView: ViewGroup? = null

    /**
     * 当前显示的view
     */
    private var mCurrentView: View? = null

    /**
     * 当前显示的view
     * @return
     */
    override val currentView: View
        get() = mCurrentView!!

    /**
     * 上下文
     * @return
     */
    override val context: Context
        get() = replaceView.context

    /**
     * 设置要展示的view
     * @param view
     */
    override fun showView(view: View) {
        if (mParentView == null) {
            init()
        }
        if (mCurrentView != view) {
            //移除旧的View
            if (mCurrentView != null) {
                mParentView?.removeView(mCurrentView)
            }
            //添加新的View
            mCurrentView = view
            mParentView?.addView(mCurrentView, mParentView?.layoutParams)
        }
    }

    private fun init() {
        mParentView =
            if (replaceView.parent != null) {
                replaceView as ViewGroup
            } else {
                replaceView.rootView.findViewById(R.id.content)
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
     * 恢复到需替换的布局view
     */
    override fun restoreView() {
        //移除旧的View
        if (mCurrentView != null) {
            mParentView?.removeView(mCurrentView)
            mCurrentView = null
        }
    }

}