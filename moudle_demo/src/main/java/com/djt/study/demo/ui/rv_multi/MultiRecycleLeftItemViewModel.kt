package com.djt.study.demo.ui.rv_multi

import android.databinding.ObservableField
import com.djt.mvvm.base.MultiItemViewModel
import com.djt.mvvm.binding.command.BindingAction
import com.djt.mvvm.binding.command.BindingCommand
import com.djt.base.utils.ToastUtils

/**
 * Create Author：goldze
 * Create Date：2019/01/25
 * Description：
 */

class MultiRecycleLeftItemViewModel(@NonNull viewModel: MultiRecycleViewModel, text: String) :
    MultiItemViewModel<MultiRecycleViewModel>(viewModel) {
    var text: ObservableField<String> = ObservableField("")

    //条目的点击事件
    var itemClick = BindingCommand(object : BindingAction() {
        @Override
        fun call() {
            //拿到position
            val position = viewModel.observableList.indexOf(this@MultiRecycleLeftItemViewModel)
            ToastUtils.showShort("position：$position")
        }
    })

    init {
        this.text.set(text)
    }
}
