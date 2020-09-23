package ${moduleName}

import android.os.Bundle
import android.view.View
import com.library.common.base.BaseListFragment
import ${packageName}.R
import ${packageName}.databinding.Fragment${fragmentClass}Binding
import kotlinx.android.synthetic.main.${layoutName}.*

/**
 * @author yangbw
 */
class ${fragmentClass}Fragment : BaseListFragment<${fragmentClass}ViewModel, Fragment${fragmentClass}Binding,
    ${fragmentClass}Adapter,${ktBeanName}>(){

    companion object {
        fun newInstance() = ${fragmentClass}Fragment()
    }

    override fun getLayoutId() = R.layout.${layoutName}

    override fun getReplaceView(): View = smartRefreshLayout

    override fun initRecyclerView() {
        mSmartRefreshLayout = smartRefreshLayout
        mRecyclerView = recyclerView
        mAdapter = ${fragmentClass}Adapter()
    }

    override fun init(savedInstanceState: Bundle?) {


    }

    override fun loadPageListData(pageNo: Int) {

    }

}
