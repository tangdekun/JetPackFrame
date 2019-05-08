package com.djt.study.demo.ui.network

import android.app.Application
import android.databinding.ObservableArrayList
import android.databinding.ObservableBoolean
import android.databinding.ObservableList

import com.djt.study.demo.R
import com.djt.study.demo.data.DemoRepository
import com.djt.study.demo.entity.DemoEntity
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import com.djt.mvvm.base.BaseViewModel
import com.djt.mvvm.binding.command.BindingAction
import com.djt.mvvm.binding.command.BindingCommand
import com.djt.base.bus.event.SingleLiveEvent
import com.djt.base.http.BaseResponse
import com.djt.base.http.ResponseThrowable
import com.djt.base.utils.RxUtils
import com.djt.base.utils.ToastUtils
import me.tatarka.bindingcollectionadapter2.ItemBinding
import com.djt.mvvm.BR

/**
 * Created by goldze on 2017/7/17.
 */

class NetWorkViewModel(@NonNull application: Application, repository: DemoRepository) :
    BaseViewModel<DemoRepository>(application, repository) {
    var deleteItemLiveData: SingleLiveEvent<NetWorkItemViewModel> = SingleLiveEvent()
    //封装一个界面发生改变的观察者
    var uc = UIChangeObservable()

    //给RecyclerView添加ObservableList
    var observableList: ObservableList<NetWorkItemViewModel> = ObservableArrayList()
    //给RecyclerView添加ItemBinding
    var itemBinding = ItemBinding.of(BR.viewModel, R.layout.demo_item_network)
    //下拉刷新
    var onRefreshCommand = BindingCommand(object : BindingAction() {
        @Override
        fun call() {
            ToastUtils.showShort("下拉刷新")
            requestNetWork()
        }
    })
    //上拉加载
    var onLoadMoreCommand = BindingCommand(object : BindingAction() {
        @Override
        fun call() {
            if (observableList.size() > 50) {
                ToastUtils.showLong("兄dei，你太无聊啦~崩是不可能的~")
                uc.finishLoadmore.set(!uc.finishLoadmore.get())
                return
            }
            //模拟网络上拉加载更多
            addSubscribe(model.simulationLoadMore()
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .doOnSubscribe(object : Consumer<Disposable>() {
                    @Override
                    @Throws(Exception::class)
                    fun accept(disposable: Disposable) {
                        ToastUtils.showShort("上拉加载")
                    }
                })
                .subscribe(object : Consumer<DemoEntity>() {
                    @Override
                    @Throws(Exception::class)
                    fun accept(entity: DemoEntity) {
                        for (itemsEntity in entity.getItems()) {
                            val itemViewModel = NetWorkItemViewModel(this@NetWorkViewModel, itemsEntity)
                            //双向绑定动态添加Item
                            observableList.add(itemViewModel)
                        }
                        //刷新完成收回
                        uc.finishLoadmore.set(!uc.finishLoadmore.get())
                    }
                })
            )
        }
    })

    inner class UIChangeObservable {
        //下拉刷新完成
        var finishRefreshing = ObservableBoolean(false)
        //上拉加载完成
        var finishLoadmore = ObservableBoolean(false)
    }

    /**
     * 网络请求方法，在ViewModel中调用Model层，通过Okhttp+Retrofit+RxJava发起请求
     */
    fun requestNetWork() {
        //建议使用addSubscribe()套一层，请求与View周期同步
        //addSubscribe();
        model.demoGet()
            .compose(RxUtils.bindToLifecycle(getLifecycleProvider())) //请求与View周期同步（过度期，尽量少使用）
            .compose(RxUtils.schedulersTransformer()) //线程调度
            .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
            .doOnSubscribe(object : Consumer<Disposable>() {
                @Override
                @Throws(Exception::class)
                fun accept(disposable: Disposable) {
                    showDialog("正在请求...")
                }
            })
            .subscribe(object : Consumer<BaseResponse<DemoEntity>>() {
                @Override
                @Throws(Exception::class)
                fun accept(response: BaseResponse<DemoEntity>) {
                    //清除列表
                    observableList.clear()
                    //请求成功
                    if (response.getCode() === 1) {
                        for (entity in response.getResult().getItems()) {
                            val itemViewModel = NetWorkItemViewModel(this@NetWorkViewModel, entity)
                            //双向绑定动态添加Item
                            observableList.add(itemViewModel)
                        }
                    } else {
                        //code错误时也可以定义Observable回调到View层去处理
                        ToastUtils.showShort("数据错误")
                    }
                }
            }, object : Consumer<ResponseThrowable>() {
                @Override
                @Throws(Exception::class)
                fun accept(throwable: ResponseThrowable) {
                    //关闭对话框
                    dismissDialog()
                    //请求刷新完成收回
                    uc.finishRefreshing.set(!uc.finishRefreshing.get())
                    ToastUtils.showShort(throwable.message)
                }
            }, object : Action() {
                @Override
                @Throws(Exception::class)
                fun run() {
                    //关闭对话框
                    dismissDialog()
                    //请求刷新完成收回
                    uc.finishRefreshing.set(!uc.finishRefreshing.get())
                }
            })
    }

    /**
     * 删除条目
     *
     * @param netWorkItemViewModel
     */
    fun deleteItem(netWorkItemViewModel: NetWorkItemViewModel) {
        //点击确定，在 observableList 绑定中删除，界面立即刷新
        observableList.remove(netWorkItemViewModel)
    }

    /**
     * 获取条目下标
     *
     * @param netWorkItemViewModel
     * @return
     */
    fun getItemPosition(netWorkItemViewModel: NetWorkItemViewModel): Int {
        return observableList.indexOf(netWorkItemViewModel)
    }

    @Override
    fun onDestroy() {
        super.onDestroy()
    }
}
