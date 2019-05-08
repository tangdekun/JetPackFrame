package com.djt.study.demo.ui.network

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.pm.ActivityInfo
import android.databinding.Observable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.afollestad.materialdialogs.DialogAction
import com.afollestad.materialdialogs.MaterialDialog

import com.djt.study.demo.R
import com.djt.study.demo.app.AppViewModelFactory
import com.djt.mvvm.base.BaseFragment
import com.djt.base.utils.MaterialDialogUtils
import com.djt.base.utils.ToastUtils
import com.djt.study.demo.databinding.DemoFragmentNetworkBinding
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter
import com.djt.mvvm.BR

/**
 * Created by goldze on 2017/7/17.
 * 网络请求列表界面
 */

class NetWorkFragment : BaseFragment<DemoFragmentNetworkBinding, NetWorkViewModel>() {
    @Override
    fun initParam() {
        super.initParam()
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
    }

    @Override
    fun initContentView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle): Int {
        return R.layout.demo_fragment_network
    }

    @Override
    fun initVariableId(): Int {
        return BR.viewModel
    }

    @Override
    fun initViewModel(): NetWorkViewModel {
        val factory = AppViewModelFactory.getInstance(getActivity().getApplication())
        return ViewModelProviders.of(this, factory).get(NetWorkViewModel::class.java)
    }

    @Override
    fun initData() {
        //给RecyclerView添加Adpter，请使用自定义的Adapter继承BindingRecyclerViewAdapter，重写onBindBinding方法，里面有你要的Item对应的binding对象。
        // Adapter属于View层的东西, 不建议定义到ViewModel中绑定，以免内存泄漏
        binding.setAdapter(BindingRecyclerViewAdapter())
        //请求网络数据
        viewModel.requestNetWork()
    }

    @Override
    fun initViewObservable() {
        //监听下拉刷新完成
        viewModel.uc.finishRefreshing.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            @Override
            fun onPropertyChanged(observable: Observable, i: Int) {
                //结束刷新
                binding.twinklingRefreshLayout.finishRefreshing()
            }
        })
        //监听上拉加载完成
        viewModel.uc.finishLoadmore.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            @Override
            fun onPropertyChanged(observable: Observable, i: Int) {
                //结束刷新
                binding.twinklingRefreshLayout.finishLoadmore()
            }
        })
        //监听删除条目
        viewModel.deleteItemLiveData.observe(this, object : Observer<NetWorkItemViewModel>() {
            @Override
            fun onChanged(@Nullable netWorkItemViewModel: NetWorkItemViewModel) {
                val index = viewModel.getItemPosition(netWorkItemViewModel)
                //删除选择对话框
                MaterialDialogUtils.showBasicDialog(
                    getContext(),
                    "提示",
                    "是否删除【" + netWorkItemViewModel.entity.get().getName() + "】？ position：" + index
                )
                    .onNegative(object : MaterialDialog.SingleButtonCallback() {
                        @Override
                        fun onClick(@NonNull dialog: MaterialDialog, @NonNull which: DialogAction) {
                            ToastUtils.showShort("取消")
                        }
                    }).onPositive(object : MaterialDialog.SingleButtonCallback() {
                        @Override
                        fun onClick(@NonNull dialog: MaterialDialog, @NonNull which: DialogAction) {
                            viewModel.deleteItem(netWorkItemViewModel)
                        }
                    }).show()
            }
        })
    }
}
