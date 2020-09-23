package ${moduleName}

import androidx.databinding.DataBindingUtil
import com.library.common.base.BaseAdapter
import com.library.common.view.baseviewholder.CommonViewHolder
import ${packageName}.R
import ${packageName}.databinding.ItemActivity${activityClass}Binding
import java.util.*

/**
 * @author yangbw
 */
class ${activityClass}Adapter : BaseAdapter<${ktBeanName}>(R.layout.item_${layoutName}, ArrayList()) {

     override fun onItemViewHolderCreated(viewHolder: CommonViewHolder, viewType: Int) {
         DataBindingUtil.bind<ItemActivity${activityClass}Binding>(viewHolder.itemView)
     }

     override fun convert(helper: CommonViewHolder, item: ${ktBeanName}) {
        val itemListBinding = helper.getBinding<ItemActivity${activityClass}Binding>()
        if (itemListBinding != null) {

        }
     }

 }
