package ${moduleName}

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.library.common.base.BaseActivity
import ${packageName}.R
import ${packageName}.databinding.Activity${activityClass}Binding
import kotlinx.android.synthetic.main.${layoutName}.*

/**
 * @author yangbw
 * @date
 */
class ${activityClass}Activity : BaseActivity<${activityClass}ViewModel, Activity${activityClass}Binding>(){

    companion object {
        fun launch(context: Context) {
            context.startActivity(Intent().apply {
                setClass(context, ${activityClass}Activity::class.java)
            })
        }
    }

    override fun getLayoutId() = R.layout.${layoutName};

    override fun getReplaceView(): View = ${layoutName}

    override fun init(savedInstanceState: Bundle?) {


    }

}
