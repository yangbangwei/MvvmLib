package ${moduleName}

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.library.common.base.BaseListActivity
import ${packageName}.R
import ${packageName}.databinding.Activity${activityClass}Binding
import kotlinx.android.synthetic.main.${layoutName}.*

/**
 * @author yangbw
 */
class ${activityClass}Activity : BaseListActivity<${activityClass}ViewModel, Activity${activityClass}Binding,
        ${activityClass}Adapter,${ktBeanName}>(){

    companion object {
        fun launch(context: Context) {
            context.startActivity(Intent().apply {
                setClass(context, ${activityClass}Activity::class.java)
            })
        }
    }

    override fun getLayoutId() = R.layout.${layoutName}

    override fun getReplaceView(): View = smartRefreshLayout

    override fun initRecyclerView() {
        mSmartRefreshLayout = smartRefreshLayout
        mRecyclerView = recyclerView
        mAdapter = ${activityClass}Adapter()
    }

    override fun init(savedInstanceState: Bundle?) {


    }

    override fun loadPageListData(pageNo: Int) {

    }

}
