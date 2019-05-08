package com.djt.study.demo.ui.base.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by goldze on 2017/7/17.
 * FragmentPager适配器
 */

class BaseFragmentPagerAdapter//使用构造方法来将数据传进去
    (
    fm: FragmentManager, private val list: List<Fragment>//ViewPager要填充的fragment列表
    , private val title: List<String>//tab中的title文字列表
) : FragmentPagerAdapter(fm) {

    //返回FragmentPager的个数
    val count: Int
        @Override
        get() = list.size()

    @Override
    fun getItem(position: Int): Fragment {//获得position中的fragment来填充
        return list[position]
    }

    //FragmentPager的标题,如果重写这个方法就显示不出tab的标题内容
    @Override
    fun getPageTitle(position: Int): CharSequence {
        return title[position]
    }
}
