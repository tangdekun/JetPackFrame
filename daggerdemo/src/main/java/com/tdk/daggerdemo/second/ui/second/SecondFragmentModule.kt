package com.tdk.daggerdemo.second.ui.second

import androidx.fragment.app.Fragment
import com.tdk.daggerdemo.di.key.FragmentKey
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap

/**
 * @Author tangdekun
 * @Date 2018/7/30-16:34
 * @Email tangdekun0924@gmail.com
 */
@Module(subcomponents = [SecondFragmentComponent::class])
interface SecondFragmentModule {

    @Binds
    @IntoMap
    @FragmentKey(SecondFragment::class)
    fun bindYourActivityInjectorFactory(builder: SecondFragmentComponent.Builder): AndroidInjector.Factory<Fragment>


}