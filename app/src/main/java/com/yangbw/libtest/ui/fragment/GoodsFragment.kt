package com.yangbw.libtest.ui.fragment

import android.os.Bundle
import android.view.View
import com.gyf.immersionbar.ktx.immersionBar
import com.library.common.base.BaseFragment
import com.yangbw.libtest.R
import com.yangbw.libtest.databinding.FragmentGoodsBinding
import com.yangbw.libtest.ui.viewmodel.GoodsViewModel
import kotlinx.android.synthetic.main.fragment_goods.*
import utils.ActionBarUtils

/**
 * 商品分类
 *
 * @author :yangbw
 * @date :2020/9/18
 */
class GoodsFragment : BaseFragment<GoodsViewModel, FragmentGoodsBinding>() {

    companion object {
        fun newInstance() = GoodsFragment()
    }

    override fun getLayoutId() = R.layout.fragment_goods

    override fun getReplaceView(): View = fragment_goods

    override fun initImmersionBar() {
        immersionBar {
            autoStatusBarDarkModeEnable(true)
            statusBarColor(R.color.white)
            titleBar(toolbar)
        }
    }

    override fun init(savedInstanceState: Bundle?) {
        ActionBarUtils.setToolBarTitleText(toolbar, "分类")
    }

}
