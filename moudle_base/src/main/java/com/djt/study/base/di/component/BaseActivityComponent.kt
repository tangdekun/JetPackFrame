package com.djt.study.base.di.component

import com.djt.study.base.base.BaseActivity
import dagger.Subcomponent
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector


/**
 * @Author tangdekun
 * @Date 2018/8/5-11:10
 * @Email tangdekun0924@gmail.com
 */
@Subcomponent(modules = [(AndroidInjectionModule::class)])
interface BaseActivityComponent : AndroidInjector<BaseActivity> {
    //每一个继承BaseActivity的Activity，都共享同一个SubComponent
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Factory<BaseActivity>
}