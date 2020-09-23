package ${moduleName}

import androidx.databinding.DataBindingUtil
import com.library.common.base.BaseAdapter
import com.library.common.view.baseviewholder.CommonViewHolder
import ${packageName}.R
import ${packageName}.databinding.ItemFragment${fragmentClass}Binding
import java.util.*

/**
 * @author yangbw
 */
class ${fragmentClass}Adapter : BaseAdapter<${ktBeanName}>(R.layout.item_${layoutName}, ArrayList()) {

     override fun onItemViewHolderCreated(viewHolder: CommonViewHolder, viewType: Int) {
         DataBindingUtil.bind<ItemFragment${fragmentClass}Binding>(viewHolder.itemView)
     }

     override fun convert(helper: CommonViewHolder, item: ${ktBeanName}) {
        val itemListBinding = helper.getBinding<ItemFragment${fragmentClass}Binding>()
        if (itemListBinding != null) {

        }
     }

 }
