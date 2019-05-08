package com.djt.study.demo.ui.viewpager.vm

import android.app.Application
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import com.djt.study.demo.R
import com.djt.mvvm.base.BaseViewModel
import com.djt.mvvm.binding.command.BindingCommand
import com.djt.mvvm.binding.command.BindingConsumer
import com.djt.base.bus.event.SingleLiveEvent
import com.djt.base.utils.ToastUtils
import me.tatarka.bindingcollectionadapter2.BindingViewPagerAdapter
import me.tatarka.bindingcollectionadapter2.ItemBinding
import com.djt.mvvm.BR

/**
 * 所有例子仅做参考,千万不要把它当成一种标准,毕竟主打的不是例子,业务场景繁多,理解如何使用才最重要。
 * Created by goldze on 2018/7/18.
 */

class ViewPagerViewModel(@NonNull application: Application) : BaseViewModel(application) {
    var itemClickEvent: SingleLiveEvent<String> = SingleLiveEvent()

    //给ViewPager添加ObservableList
    var items: ObservableList<ViewPagerItemViewModel> = ObservableArrayList()
    //给ViewPager添加ItemBinding
    var itemBinding = ItemBinding.of(BR.viewModel, R.layout.demo_item_viewpager)
    //给ViewPager添加PageTitle
    val pageTitles: BindingViewPagerAdapter.PageTitles<ViewPagerItemViewModel> =
        object : BindingViewPagerAdapter.PageTitles<ViewPagerItemViewModel>() {
            @Override
            fun getPageTitle(position: Int, item: ViewPagerItemViewModel): CharSequence {
                return "条目$position"
            }
        }
    //ViewPager切换监听
    var onPageSelectedCommand: BindingCommand<Integer> = BindingCommand(object : BindingConsumer<Integer>() {
        @Override
        fun call(index: Integer) {
            ToastUtils.showShort("ViewPager切换：$index")
        }
    })

    init {
        //模拟3个ViewPager页面
        for (i in 1..3) {
            val itemViewModel = ViewPagerItemViewModel(this, "第" + i + "个页面")
            items.add(itemViewModel)
        }
    }
}
