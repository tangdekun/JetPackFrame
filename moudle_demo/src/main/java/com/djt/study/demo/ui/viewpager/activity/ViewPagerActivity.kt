package com.djt.study.demo.ui.viewpager.activity

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.design.widget.TabLayout
import com.djt.study.demo.R
import com.djt.study.demo.databinding.DemoFragmentViewpagerBinding
import com.djt.study.demo.ui.viewpager.adapter.ViewPagerBindingAdapter
import com.djt.study.demo.ui.viewpager.vm.ViewPagerViewModel
import com.djt.mvvm.base.BaseActivity
import com.djt.base.utils.ToastUtils
import com.djt.mvvm.BR

/**
 * ViewPager绑定的例子, 更多绑定方式，请参考 https://github.com/evant/binding-collection-adapter
 * 所有例子仅做参考,千万不要把它当成一种标准,毕竟主打的不是例子,业务场景繁多,理解如何使用才最重要。
 * Created by goldze on 2018/7/18.
 */

class ViewPagerActivity : BaseActivity<DemoFragmentViewpagerBinding, ViewPagerViewModel>() {

    @Override
    fun initContentView(savedInstanceState: Bundle): Int {
        return R.layout.demo_fragment_viewpager
    }

    @Override
    fun initVariableId(): Int {
        return BR.viewModel
    }


    @Override
    fun initData() {
        // 使用 TabLayout 和 ViewPager 相关联
        binding.tabs.setupWithViewPager(binding.viewPager)
        binding.viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tabs))
        //给ViewPager设置adapter
        binding.setAdapter(ViewPagerBindingAdapter())
    }

    @Override
    fun initViewObservable() {
        viewModel.itemClickEvent.observe(this, object : Observer<String>() {
            @Override
            fun onChanged(@Nullable text: String) {
                ToastUtils.showShort("position：$text")
            }
        })
    }
}
