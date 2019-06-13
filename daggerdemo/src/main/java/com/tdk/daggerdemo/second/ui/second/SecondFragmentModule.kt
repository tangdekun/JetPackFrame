package com.tdk.daggerdemo.second.ui.second

import androidx.lifecycle.ViewModelProviders
import com.tdk.daggerdemo.SecondFragmentComponent
import com.tdk.daggerdemo.base.BaseFragment
import com.tdk.daggerdemo.di.factory.FragemntComponent
import dagger.Module
import dagger.Provides

/**
 * @Author tangdekun
 * @Date 2018/7/30-16:34
 * @Email tangdekun0924@gmail.com
 */
@Module(subcomponents = [SecondFragmentComponent::class])
class SecondFragmentModule {

    @Provides
    fun provideSecondViewModel(fragment: BaseFragment): SecondViewModel {
        return ViewModelProviders.of(fragment).get(SecondViewModel::class.java)
    }


}