package com.tdk.daggerdemo.di.module

import com.tdk.daggerdemo.base.BaseFragment
import com.tdk.daggerdemo.di.factory.FragemntComponent
import com.tdk.daggerdemo.di.key.FragmentKey
import com.tdk.daggerdemo.second.ui.second.SecondFragment
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap

/**
 * @Author tangdekun
 * @Date 2019/5/10-15:04
 * @Email tangdekun0924@gmail.com
 */
@Module(subcomponents = [FragemntComponent::class])
abstract class FragmentModule {
    @Binds
    @IntoMap
    @FragmentKey(SecondFragment::class)
    abstract fun bindFragmentInjectorFactory(factory: FragemntComponent.Factory): AndroidInjector.Factory<out BaseFragment>
}