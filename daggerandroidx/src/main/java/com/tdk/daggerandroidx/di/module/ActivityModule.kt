package com.tdk.daggerdemo.di.module

import com.tdk.daggerdemo.MainActivity
import com.tdk.daggerdemo.MainComponent
import com.tdk.daggerdemo.di.key.ActivityKey
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap

/**
 * @Author tangdekun
 * @Date 2019/5/10-15:04
 * @Email tangdekun0924@gmail.com
 */
@Module(subcomponents = [MainComponent::class])
abstract class ActivityModule {

    @Binds
    @IntoMap
    @ActivityKey(MainActivity::class)
    abstract fun bindYourActivityInjectorFactory(factory: MainComponent.Builder): AndroidInjector.Factory<*>
}