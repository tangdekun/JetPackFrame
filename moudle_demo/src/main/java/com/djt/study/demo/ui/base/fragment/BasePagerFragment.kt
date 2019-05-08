package com.djt.study.demo.ui.base.fragment


import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import com.djt.study.demo.R
import com.djt.mvvm.BR
import com.djt.study.demo.databinding.DemoFragmentBasePagerBinding
import com.djt.study.demo.ui.base.adapter.BaseFragmentPagerAdapter
import com.djt.mvvm.base.BaseFragment
import com.djt.mvvm.base.BaseViewModel

/**
 * Created by goldze on 2017/7/17.
 * 抽取的二级BasePagerFragment
 */

abstract class BasePagerFragment : BaseFragment<DemoFragmentBasePagerBinding, BaseViewModel>() {

    private var mFragments: List<Fragment>? = null
    private var titlePager: List<String>? = null

    protected abstract fun pagerFragment(): List<Fragment>

    protected abstract fun pagerTitleString(): List<String>

    @Override
    fun initContentView(inflater: LayoutInflater, @Nullable container: ViewGroup, @Nullable savedInstanceState: Bundle): Int {
        return R.layout.demo_fragment_base_pager
    }

    @Override
    fun initVariableId(): Int {
        return BR.viewModel
    }

    @Override
    fun initData() {
        mFragments = pagerFragment()
        titlePager = pagerTitleString()
        //设置Adapter
        val pagerAdapter = BaseFragmentPagerAdapter(getChildFragmentManager(), mFragments, titlePager)
        binding.viewPager.setAdapter(pagerAdapter)
        binding.tabs.setupWithViewPager(binding.viewPager)
        binding.viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tabs))
    }

    @Override
    fun initViewObservable() {

    }
}
