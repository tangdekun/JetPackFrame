package com.djt.study.demo.ui.rv_multi

import android.app.Application
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import com.djt.study.demo.R
import com.djt.mvvm.base.BaseViewModel
import com.djt.mvvm.base.MultiItemViewModel
import me.tatarka.bindingcollectionadapter2.ItemBinding
import me.tatarka.bindingcollectionadapter2.OnItemBind
import com.djt.mvvm.BR

/**
 * Create Author：goldze
 * Create Date：2019/01/25
 * Description：
 */

class MultiRecycleViewModel(@NonNull application: Application) : BaseViewModel(application) {

    //给RecyclerView添加ObservableList
    var observableList: ObservableList<MultiItemViewModel> = ObservableArrayList()
    //RecyclerView多布局添加ItemBinding
    var itemBinding = ItemBinding.of(object : OnItemBind<MultiItemViewModel>() {
        @Override
        fun onItemBind(itemBinding: ItemBinding, position: Int, item: MultiItemViewModel) {
            //通过item的类型, 动态设置Item加载的布局
            val itemType = item.getItemType() as String
            if (MultiRecycleType_Head.equals(itemType)) {
                //设置头布局
                itemBinding.set(BR.viewModel, R.layout.demo_item_multi_head)
            } else if (MultiRecycleType_Left.equals(itemType)) {
                //设置左布局
                itemBinding.set(BR.viewModel, R.layout.demo_item_multi_rv_left)
            } else if (MultiRecycleType_Right.equals(itemType)) {
                //设置右布局
                itemBinding.set(BR.viewModel, R.layout.demo_item_multi_rv_right)
            }
        }
    })

    init {
        //模拟10个条目，数据源可以来自网络
        for (i in 0..19) {
            if (i == 0) {
                val item = MultiRecycleHeadViewModel(this)
                //条目类型为头布局
                item.multiItemType(MultiRecycleType_Head)
                observableList.add(item)
            } else {
                val text = "我是第" + i + "条"
                if (i % 2 == 0) {
                    val item = MultiRecycleLeftItemViewModel(this, text)
                    //条目类型为左布局
                    item.multiItemType(MultiRecycleType_Left)
                    observableList.add(item)
                } else {
                    val item = MultiRecycleRightItemViewModel(this, text)
                    //条目类型为右布局
                    item.multiItemType(MultiRecycleType_Right)
                    observableList.add(item)
                }
            }
        }
    }

    companion object {
        private val MultiRecycleType_Head = "head"
        private val MultiRecycleType_Left = "left"
        private val MultiRecycleType_Right = "right"
    }
}
