package com.djt.study.demo.ui.tab_bar.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.djt.study.demo.R
import com.djt.mvvm.base.BaseFragment
import com.djt.mvvm.BR

/**
 * Created by goldze on 2018/7/18.
 */

class TabBar1Fragment : BaseFragment() {
    @Override
    fun initContentView(inflater: LayoutInflater, @Nullable container: ViewGroup, @Nullable savedInstanceState: Bundle): Int {
        return R.layout.demo_fragment_tab_bar_1
    }

    @Override
    fun initVariableId(): Int {
        return BR.viewModel
    }
}
