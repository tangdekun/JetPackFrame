package com.djt.study.demo.ui.rv_multi

import com.djt.mvvm.base.BaseViewModel
import com.djt.mvvm.base.MultiItemViewModel
import com.djt.mvvm.binding.command.BindingAction
import com.djt.mvvm.binding.command.BindingCommand
import com.djt.base.utils.ToastUtils

/**
 * Create Author：goldze
 * Create Date：2019/01/25
 * Description：
 */

class MultiRecycleHeadViewModel(@NonNull viewModel: BaseViewModel) : MultiItemViewModel(viewModel) {

    //条目的点击事件
    var itemClick = BindingCommand(object : BindingAction() {
        @Override
        fun call() {
            ToastUtils.showShort("我是头布局")
        }
    })
}
