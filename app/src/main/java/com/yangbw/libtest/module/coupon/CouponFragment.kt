package com.yangbw.libtest.module.coupon

import android.os.Bundle
import android.view.Gravity
import com.library.common.base.BaseDialogFragment
import com.yangbw.libtest.R
import com.yangbw.libtest.databinding.FragmentCouponBinding
import kotlinx.android.synthetic.main.fragment_coupon.*

/**
 *
 * 活动弹窗
 *
 * @author :yangbw
 * @date :2020/9/17
 */
class CouponFragment : BaseDialogFragment<CouponViewModel, FragmentCouponBinding>() {

    companion object {
        fun newInstance() = CouponFragment()
    }

    override fun getLayoutId() = R.layout.fragment_coupon

    override fun init(savedInstanceState: Bundle?) {
        //基本配置
        mIsCancelable = true
        mGravity = Gravity.CENTER
        mDimAmount = 0.4f
        mHeight = 0.7f
        mWidth = 0.8f
        mWindowAnimations = R.style.CustomDialog
        //点击事件
        mBinding.run {
            tvOk.setOnClickListener {
                onOkClick?.let { it1 -> it1() }
                dismiss()
            }
            ivClose.setOnClickListener {
                dismiss()
            }
        }
        //界面显示
        mViewModel.mData.observe(this) {
            val couponAdapter = CouponAdapter().apply {
                data.addAll(it)
            }
            couponAdapter.addChildClickViewIds(R.id.btn_get)
            couponAdapter.setOnItemChildClickListener { adapter, view, position ->
                val couponData = adapter.getItem(position) as CouponData
                showToast(String.format(getString(R.string.get_red_packet_tips), couponData.derate))
            }
            recyclerView.adapter = couponAdapter
        }
        mViewModel.onStart()
    }

    private var onOkClick: (() -> Unit)? = null

    fun setOnOkClickListener(onOkClick: () -> Unit) {
        this.onOkClick = onOkClick
    }

}