package com.tdk.daggerdemo

import dagger.Subcomponent
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector


/**
 * @Author tangdekun
 * @Date 2018/7/30-10:53
 * @Email tangdekun0924@gmail.com
 */
@Subcomponent(modules = [AndroidInjectionModule::class])
interface MainComponent : AndroidInjector<MainActivity> {
    @Subcomponent.Factory
    abstract class Builder : AndroidInjector.Factory<MainActivity>
}