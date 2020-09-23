package ${moduleName}

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.library.common.base.BaseFragment
import ${packageName}.R
import kotlinx.android.synthetic.main.${layoutName}.*

/**
 * @author yangbw
 */
class ${fragmentClass}Fragment : BaseFragment<${fragmentClass}ViewModel, ViewDataBinding>(){

    companion object {
        fun newInstance() = ${fragmentClass}Fragment()
    }

    override fun getLayoutId() = R.layout.${layoutName}

    override fun getReplaceView(): View = ${layoutName}

    override fun init(savedInstanceState: Bundle?) {


    }
}
