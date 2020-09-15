package com.yangbw.libtest.module.menu

import androidx.lifecycle.MutableLiveData
import com.library.common.em.RequestDisplay
import com.library.common.mvvm.BaseViewModel
import com.yangbw.libtest.api.ApiService

/**
 * @author yangbw
 * @date
 */
class MenuViewModel : BaseViewModel<ApiService>() {

    val mMenuData = MutableLiveData<List<MenuData>>()

    public override fun onStart() {
    }

    fun menuList() {
        launchOnlyResult(
            block = {
                getApiService().menuList(1)
            },
            success = {
                mMenuData.value = it
            },
            reTry = {
                menuList()
            }
        )
    }

}
