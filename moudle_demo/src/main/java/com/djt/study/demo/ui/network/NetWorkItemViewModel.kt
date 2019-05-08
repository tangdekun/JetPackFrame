package com.djt.study.demo.ui.network

import android.databinding.ObservableField
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.djt.study.demo.R
import com.djt.study.demo.entity.DemoEntity
import com.djt.study.demo.ui.network.detail.DetailFragment
import com.djt.mvvm.base.ItemViewModel
import com.djt.mvvm.binding.command.BindingAction
import com.djt.mvvm.binding.command.BindingCommand
import com.djt.base.utils.ToastUtils

/**
 * Created by goldze on 2017/7/17.
 */

class NetWorkItemViewModel(@NonNull viewModel: NetWorkViewModel, entity: DemoEntity.ItemsEntity) :
    ItemViewModel<NetWorkViewModel>(viewModel) {
    var entity: ObservableField<DemoEntity.ItemsEntity> = ObservableField()
    var drawableImg: Drawable

    /**
     * 获取position的方式有很多种,indexOf是其中一种，常见的还有在Adapter中、ItemBinding.of回调里
     *
     * @return
     */
    val position: Int
        get() = viewModel.getItemPosition(this)

    //条目的点击事件
    var itemClick = BindingCommand(object : BindingAction() {
        @Override
        fun call() {
            //这里可以通过一个标识,做出判断，已达到跳入不同界面的逻辑
            if (entity.get().getId() === -1) {
                viewModel.deleteItemLiveData.setValue(this@NetWorkItemViewModel)
            } else {
                //跳转到详情界面,传入条目的实体对象
                val mBundle = Bundle()
                mBundle.putParcelable("entity", entity.get())
                viewModel.startContainerActivity(DetailFragment::class.java!!.getCanonicalName(), mBundle)
            }
        }
    })
    //条目的长按事件
    var itemLongClick = BindingCommand(object : BindingAction() {
        @Override
        fun call() {
            //以前是使用Messenger发送事件，在NetWorkViewModel中完成删除逻辑
            //            Messenger.getDefault().send(NetWorkItemViewModel.this, NetWorkViewModel.TOKEN_NETWORKVIEWMODEL_DELTE_ITEM);
            //现在ItemViewModel中存在ViewModel引用，可以直接拿到LiveData去做删除
            ToastUtils.showShort(entity.get().getName())
        }
    })

    init {
        this.entity.set(entity)
        //ImageView的占位图片，可以解决RecyclerView中图片错误问题
        drawableImg = ContextCompat.getDrawable(viewModel.getApplication(), R.mipmap.demo_ic_launcher)
    }
    //    /**
    //     * 可以在xml中使用binding:currentView="@{viewModel.titleTextView}" 拿到这个控件的引用, 但是强烈不推荐这样做，避免内存泄漏
    //     **/
    //    private TextView tv;
    //    //将标题TextView控件回调到ViewModel中
    //    public BindingCommand<TextView> titleTextView = new BindingCommand(new BindingConsumer<TextView>() {
    //        @Override
    //        public void call(TextView tv) {
    //            NetWorkItemViewModel.this.tv = tv;
    //        }
    //    });
}
