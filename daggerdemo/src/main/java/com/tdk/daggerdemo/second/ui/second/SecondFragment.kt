package com.tdk.daggerdemo.second.ui.second

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.tdk.daggerdemo.R
import com.tdk.daggerdemo.base.BaseFragment

class SecondFragment : BaseFragment() {
    override fun getLayoutId(): Int {
        return R.layout.second_fragment
    }


    companion object {
        fun newInstance(): Fragment = SecondFragment()
    }

    private lateinit var viewModel: SecondViewModel


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SecondViewModel::class.java)

    }

}
