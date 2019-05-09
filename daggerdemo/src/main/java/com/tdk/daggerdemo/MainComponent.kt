package com.tdk.daggerdemo

import dagger.Component

/**
 * @Author tangdekun
 * @Date 2018/7/30-10:53
 * @Email tangdekun0924@gmail.com
 */
//@Component(modules = [(MainModule::class)], dependencies = [AppComponent::class])
//@CustomScopeName
@Component
interface MainComponent {
    fun inject(mainActivity: MainActivity)
}