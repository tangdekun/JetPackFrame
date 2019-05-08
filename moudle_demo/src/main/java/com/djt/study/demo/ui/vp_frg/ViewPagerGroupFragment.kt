package com.djt.study.demo.ui.vp_frg

import android.support.v4.app.Fragment
import com.djt.study.demo.ui.base.fragment.BasePagerFragment
import com.djt.study.demo.ui.tab_bar.fragment.TabBar1Fragment
import com.djt.study.demo.ui.tab_bar.fragment.TabBar2Fragment
import com.djt.study.demo.ui.tab_bar.fragment.TabBar3Fragment
import com.djt.study.demo.ui.tab_bar.fragment.TabBar4Fragment

import java.util.ArrayList

/**
 * Create Author：goldze
 * Create Date：2019/01/25
 * Description：ViewPager+Fragment的实现
 */

class ViewPagerGroupFragment : BasePagerFragment() {
    @Override
    protected fun pagerFragment(): List<Fragment> {
        val list = ArrayList()
        list.add(TabBar1Fragment())
        list.add(TabBar2Fragment())
        list.add(TabBar3Fragment())
        list.add(TabBar4Fragment())
        return list
    }

    @Override
    protected fun pagerTitleString(): List<String> {
        val list = ArrayList()
        list.add("page1")
        list.add("page2")
        list.add("page3")
        list.add("page4")
        return list
    }
}
