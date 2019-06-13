package com.tdk.daggerdemo.di.factory

import com.tdk.daggerandroidx.MainActivity
import com.tdk.daggerdemo.MainActivity
import dagger.Subcomponent
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector

/**
 * @Author tangdekun
 * @Date 2019/5/10-15:02
 * @Email tangdekun0924@gmail.com
 */
/**
 * @Author tangdekun
 * @Date 2018/7/30-10:53
 * @Email tangdekun0924@gmail.com
 */
@Subcomponent(modules = [AndroidInjectionModule::class])
interface ActivityComponent : AndroidInjector<MainActivity> {

    @Subcomponent.Factory
    abstract class MainActivityFactory : AndroidInjector.Factory<MainActivity>
}