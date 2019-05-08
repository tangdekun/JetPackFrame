package com.djt.study.demo.ui.rv_multi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.djt.study.demo.R
import com.djt.mvvm.base.BaseFragment
import com.djt.study.demo.databinding.DemoFragmentMultiRvBinding
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter
import com.djt.mvvm.BR

/**
 * Create Author：goldze
 * Create Date：2019/01/25
 * Description：RecycleView多布局实现
 */

class MultiRecycleViewFragment : BaseFragment<DemoFragmentMultiRvBinding, MultiRecycleViewModel>() {
    @Override
    fun initContentView(inflater: LayoutInflater, @Nullable container: ViewGroup, @Nullable savedInstanceState: Bundle): Int {
        return R.layout.demo_fragment_multi_rv
    }

    @Override
    fun initVariableId(): Int {
        return BR.viewModel
    }

    @Override
    fun initData() {
        super.initData()
        //给RecyclerView添加Adpter，请使用自定义的Adapter继承BindingRecyclerViewAdapter，重写onBindBinding方法，里面有你要的Item对应的binding对象。
        // Adapter属于View层的东西, 不建议定义到ViewModel中绑定，以免内存泄漏
        binding.setAdapter(BindingRecyclerViewAdapter())
    }
}
