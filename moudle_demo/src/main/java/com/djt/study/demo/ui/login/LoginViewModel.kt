package com.djt.study.demo.ui.login

import android.app.Application
import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.text.TextUtils
import android.view.View
import com.djt.study.demo.data.DemoRepository
import com.djt.study.demo.ui.main.DemoActivity
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import com.djt.mvvm.base.BaseViewModel
import com.djt.mvvm.binding.command.BindingAction
import com.djt.mvvm.binding.command.BindingCommand
import com.djt.mvvm.binding.command.BindingConsumer
import com.djt.base.bus.event.SingleLiveEvent
import com.djt.base.utils.RxUtils
import com.djt.base.utils.ToastUtils

/**
 * Created by goldze on 2017/7/17.
 */

class LoginViewModel(@NonNull application: Application, repository: DemoRepository) :
    BaseViewModel<DemoRepository>(application, repository) {
    //用户名的绑定
    var userName: ObservableField<String> = ObservableField("")
    //密码的绑定
    var password: ObservableField<String> = ObservableField("")
    //用户名清除按钮的显示隐藏绑定
    var clearBtnVisibility = ObservableInt()
    //封装一个界面发生改变的观察者
    var uc = UIChangeObservable()

    //清除用户名的点击事件, 逻辑从View层转换到ViewModel层
    var clearUserNameOnClickCommand = BindingCommand(object : BindingAction() {
        @Override
        fun call() {
            userName.set("")
        }
    })
    //密码显示开关  (你可以尝试着狂按这个按钮,会发现它有防多次点击的功能)
    var passwordShowSwitchOnClickCommand = BindingCommand(object : BindingAction() {
        @Override
        fun call() {
            //让观察者的数据改变,逻辑从ViewModel层转到View层，在View层的监听则会被调用
            uc.pSwitchEvent.setValue(uc.pSwitchEvent.getValue() == null || !uc.pSwitchEvent.getValue())
        }
    })
    //用户名输入框焦点改变的回调事件
    var onFocusChangeCommand: BindingCommand<Boolean> = BindingCommand(object : BindingConsumer<Boolean>() {
        @Override
        fun call(hasFocus: Boolean) {
            if (hasFocus) {
                clearBtnVisibility.set(View.VISIBLE)
            } else {
                clearBtnVisibility.set(View.INVISIBLE)
            }
        }
    })
    //登录按钮的点击事件
    var loginOnClickCommand = BindingCommand(object : BindingAction() {
        @Override
        fun call() {
            login()
        }
    })

    inner class UIChangeObservable {
        //密码开关观察者
        var pSwitchEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()
    }

    init {
        //从本地取得数据绑定到View层
        userName.set(model.getUserName())
        password.set(model.getPassword())
    }

    /**
     * 网络模拟一个登陆操作
     */
    private fun login() {
        if (TextUtils.isEmpty(userName.get())) {
            ToastUtils.showShort("请输入账号！")
            return
        }
        if (TextUtils.isEmpty(password.get())) {
            ToastUtils.showShort("请输入密码！")
            return
        }
        //RaJava模拟登录
        addSubscribe(model.simulationLogin()
            .compose(RxUtils.schedulersTransformer()) //线程调度
            .doOnSubscribe(object : Consumer<Disposable>() {
                @Override
                @Throws(Exception::class)
                fun accept(disposable: Disposable) {
                    showDialog()
                }
            })
            .subscribe(object : Consumer<Object>() {
                @Override
                @Throws(Exception::class)
                fun accept(o: Object) {
                    dismissDialog()
                    //保存账号密码
                    model.saveUserName(userName.get())
                    model.savePassword(password.get())
                    //进入DemoActivity页面
                    startActivity(DemoActivity::class.java)
                    //关闭页面
                    finish()
                }
            })
        )

    }

    @Override
    fun onDestroy() {
        super.onDestroy()
    }
}
