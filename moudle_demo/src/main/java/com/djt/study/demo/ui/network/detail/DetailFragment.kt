package com.djt.study.demo.ui.network.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup

import com.djt.study.demo.R
import com.djt.study.demo.databinding.DemoFragmentDetailBinding
import com.djt.study.demo.entity.DemoEntity
import com.djt.mvvm.base.BaseFragment
import com.djt.mvvm.BR

/**
 * Created by goldze on 2017/7/17.
 * 详情界面
 */

class DetailFragment : BaseFragment<DemoFragmentDetailBinding, DetailViewModel>() {

    private var entity: DemoEntity.ItemsEntity? = null

    @Override
    fun initParam() {
        //获取列表传入的实体
        val mBundle = getArguments()
        if (mBundle != null) {
            entity = mBundle!!.getParcelable("entity")
        }
    }

    @Override
    fun initContentView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle): Int {
        return R.layout.demo_fragment_detail
    }

    @Override
    fun initVariableId(): Int {
        return BR.viewModel
    }

    @Override
    fun initData() {
        viewModel.setDemoEntity(entity)
    }
}
