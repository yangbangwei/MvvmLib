package com.yangbw.libtest.ui.dialog

import android.os.Bundle
import android.view.Gravity
import com.library.common.base.BaseDialogFragment
import com.yangbw.libtest.R
import com.yangbw.libtest.databinding.DialogCouponBinding
import com.yangbw.libtest.entity.CouponData
import com.yangbw.libtest.ui.adapter.CouponAdapter
import com.yangbw.libtest.ui.viewmodel.CouponViewModel
import kotlinx.android.synthetic.main.dialog_coupon.*

/**
 *
 * 活动弹窗
 *
 * @author :yangbw
 * @date :2020/9/17
 */
class CouponDialog : BaseDialogFragment<CouponViewModel, DialogCouponBinding>() {

    companion object {
        fun newInstance() = CouponDialog()
    }

    override fun getLayoutId() = R.layout.dialog_coupon

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
            couponAdapter.setOnItemChildClickListener { adapter, _, position ->
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