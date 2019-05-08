package com.djt.study.demo.ui.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod

import com.djt.study.demo.BR
import com.djt.study.demo.R
import com.djt.study.demo.app.AppViewModelFactory
import com.djt.mvvm.base.BaseActivity
import com.djt.study.demo.databinding.DemoActivityLoginBinding

/**
 * 一个MVVM模式的登陆界面
 */
class LoginActivity : BaseActivity<DemoActivityLoginBinding, LoginViewModel>() {
    //ActivityLoginBinding类是databinding框架自定生成的,对应activity_login.xml
    @Override
    fun initContentView(savedInstanceState: Bundle): Int {
        return R.layout.demo_activity_login
    }

    @Override
    fun initVariableId(): Int {
        return BR.viewModel
    }

    @Override
    fun initViewModel(): LoginViewModel {
        //View持有ViewModel的引用，如果没有特殊业务处理，这个方法可以不重写
        val factory = AppViewModelFactory.getInstance(getApplication())
        return ViewModelProviders.of(this, factory).get(LoginViewModel::class.java)
    }

    @Override
    fun initViewObservable() {
        //监听ViewModel中pSwitchObservable的变化, 当ViewModel中执行【uc.pSwitchObservable.set(!uc.pSwitchObservable.get());】时会回调该方法
        viewModel.uc.pSwitchEvent.observe(this, object : Observer<Boolean>() {
            @Override
            fun onChanged(@Nullable aBoolean: Boolean) {
                //pSwitchObservable是boolean类型的观察者,所以可以直接使用它的值改变密码开关的图标
                if (viewModel.uc.pSwitchEvent.getValue()) {
                    //密码可见
                    //在xml中定义id后,使用binding可以直接拿到这个view的引用,不再需要findViewById去找控件了
                    binding.ivSwichPasswrod.setImageResource(R.mipmap.demo_show_psw_press)
                    binding.etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance())
                } else {
                    //密码不可见
                    binding.ivSwichPasswrod.setImageResource(R.mipmap.demo_show_psw)
                    binding.etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance())
                }
            }
        })
    }
}
