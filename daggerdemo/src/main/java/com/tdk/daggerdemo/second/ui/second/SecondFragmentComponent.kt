package com.tdk.daggerdemo.second.ui.second

import com.tdk.daggerdemo.CustomScopeName
import dagger.Subcomponent
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector

/**
 * @Author tangdekun
 * @Date 2018/7/30-16:35
 * @Email tangdekun0924@gmail.com
 */
@CustomScopeName
@Subcomponent(modules = [AndroidInjectionModule::class])
interface SecondFragmentComponent : AndroidInjector<SecondFragment> {
    @Subcomponent.Factory
    abstract class Builder : AndroidInjector.Factory<SecondFragment>
}