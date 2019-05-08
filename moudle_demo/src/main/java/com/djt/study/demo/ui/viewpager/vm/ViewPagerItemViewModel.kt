package com.djt.study.demo.ui.viewpager.vm

import com.djt.mvvm.base.ItemViewModel
import com.djt.mvvm.binding.command.BindingAction
import com.djt.mvvm.binding.command.BindingCommand

/**
 * 所有例子仅做参考,千万不要把它当成一种标准,毕竟主打的不是例子,业务场景繁多,理解如何使用才最重要。
 * Created by goldze on 2018/7/18.
 */

class ViewPagerItemViewModel(@NonNull viewModel: ViewPagerViewModel, var text: String) :
    ItemViewModel<ViewPagerViewModel>(viewModel) {

    var onItemClick = BindingCommand(object : BindingAction() {
        @Override
        fun call() {
            //点击之后将逻辑转到activity中处理
            viewModel.itemClickEvent.setValue(text)
        }
    })
}
