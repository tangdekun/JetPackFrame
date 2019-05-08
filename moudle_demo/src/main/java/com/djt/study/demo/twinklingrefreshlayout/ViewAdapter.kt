package com.djt.study.demo.twinklingrefreshlayout


import android.databinding.BindingAdapter
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout
import com.djt.mvvm.binding.command.BindingCommand


/**
 * Created by goldze on 2017/6/16.
 * TwinklingRefreshLayout列表刷新的绑定适配器
 */
object ViewAdapter {

    @BindingAdapter(value = { "onRefreshCommand", "onLoadMoreCommand" }, requireAll = false)
    fun onRefreshAndLoadMoreCommand(
        layout: TwinklingRefreshLayout,
        onRefreshCommand: BindingCommand?,
        onLoadMoreCommand: BindingCommand?
    ) {
        layout.setOnRefreshListener(object : RefreshListenerAdapter() {
            @Override
            fun onRefresh(refreshLayout: TwinklingRefreshLayout) {
                super.onRefresh(refreshLayout)
                if (onRefreshCommand != null) {
                    onRefreshCommand!!.execute()
                }
            }

            @Override
            fun onLoadMore(refreshLayout: TwinklingRefreshLayout) {
                super.onLoadMore(refreshLayout)
                if (onLoadMoreCommand != null) {
                    onLoadMoreCommand!!.execute()
                }
            }
        })
    }
}
