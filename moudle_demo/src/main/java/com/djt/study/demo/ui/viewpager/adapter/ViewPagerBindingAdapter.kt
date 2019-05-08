package com.djt.study.demo.ui.viewpager.adapter

import android.databinding.ViewDataBinding
import android.view.ViewGroup
import com.djt.study.demo.databinding.DemoItemViewpagerBinding
import com.djt.study.demo.ui.viewpager.vm.ViewPagerItemViewModel
import me.tatarka.bindingcollectionadapter2.BindingViewPagerAdapter

/**
 * Created by goldze on 2018/6/21.
 */

class ViewPagerBindingAdapter : BindingViewPagerAdapter<ViewPagerItemViewModel>() {

    @Override
    fun onBindBinding(
        binding: ViewDataBinding,
        variableId: Int,
        layoutRes: Int,
        position: Int,
        item: ViewPagerItemViewModel
    ) {
        super.onBindBinding(binding, variableId, layoutRes, position, item)
        //这里可以强转成ViewPagerItemViewModel对应的ViewDataBinding，
        val _binding = binding as DemoItemViewpagerBinding
    }

    @Override
    fun destroyItem(container: ViewGroup, position: Int, `object`: Object) {
        super.destroyItem(container, position, `object`)
    }
}
