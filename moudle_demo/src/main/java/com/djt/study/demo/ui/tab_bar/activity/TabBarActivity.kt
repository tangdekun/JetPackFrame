package com.djt.study.demo.ui.tab_bar.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v4.content.ContextCompat
import com.djt.study.demo.R
import com.djt.study.demo.databinding.DemoActivityTabBarBinding
import com.djt.study.demo.ui.tab_bar.fragment.TabBar1Fragment
import com.djt.study.demo.ui.tab_bar.fragment.TabBar2Fragment
import com.djt.study.demo.ui.tab_bar.fragment.TabBar3Fragment
import com.djt.study.demo.ui.tab_bar.fragment.TabBar4Fragment
import com.djt.mvvm.base.BaseActivity
import com.djt.mvvm.base.BaseViewModel
import me.majiajie.pagerbottomtabstrip.NavigationController
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener

import java.util.ArrayList
import com.djt.mvvm.BR

/**
 * 底部tab按钮的例子
 * 所有例子仅做参考,理解如何使用才最重要。
 * Created by goldze on 2018/7/18.
 */

class TabBarActivity : BaseActivity<DemoActivityTabBarBinding, BaseViewModel>() {
    private var mFragments: List<Fragment>? = null

    @Override
    fun initContentView(savedInstanceState: Bundle): Int {
        return R.layout.demo_activity_tab_bar
    }

    @Override
    fun initVariableId(): Int {
        return BR.viewModel
    }

    @Override
    fun initData() {
        //初始化Fragment
        initFragment()
        //初始化底部Button
        initBottomTab()
    }

    private fun initFragment() {
        mFragments = ArrayList()
        mFragments!!.add(TabBar1Fragment())
        mFragments!!.add(TabBar2Fragment())
        mFragments!!.add(TabBar3Fragment())
        mFragments!!.add(TabBar4Fragment())
        //默认选中第一个
        val transaction = getSupportFragmentManager().beginTransaction()
        transaction.add(R.id.frameLayout, mFragments!![0])
        transaction.commitAllowingStateLoss()
    }

    private fun initBottomTab() {
        val navigationController = binding.pagerBottomTab.material()
            .addItem(R.mipmap.demo_yingyong, "应用")
            .addItem(R.mipmap.demo_huanzhe, "工作")
            .addItem(R.mipmap.demo_xiaoxi_select, "消息")
            .addItem(R.mipmap.demo_wode_select, "我的")
            .setDefaultColor(ContextCompat.getColor(this, R.color.demo_textColorVice))
            .build()
        //底部按钮的点击事件监听
        navigationController.addTabItemSelectedListener(object : OnTabItemSelectedListener() {
            @Override
            fun onSelected(index: Int, old: Int) {
                val transaction = getSupportFragmentManager().beginTransaction()
                transaction.replace(R.id.frameLayout, mFragments!![index])
                transaction.commitAllowingStateLoss()
            }

            @Override
            fun onRepeat(index: Int) {
            }
        })
    }
}
