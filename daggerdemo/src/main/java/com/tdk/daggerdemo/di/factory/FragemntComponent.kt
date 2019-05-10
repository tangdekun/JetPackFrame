package com.tdk.daggerdemo.di.factory

import com.tdk.daggerdemo.base.BaseFragment
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
interface FragemntComponent : AndroidInjector<BaseFragment> {

    @Subcomponent.Factory
    abstract class Factory : AndroidInjector.Factory<BaseFragment>
}