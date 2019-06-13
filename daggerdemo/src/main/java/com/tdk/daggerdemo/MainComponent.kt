package com.tdk.daggerdemo

import com.tdk.daggerdemo.di.scope.ActivityScope
import com.tdk.daggerdemo.di.scope.FragmentScope
import com.tdk.daggerdemo.second.SecondActivity
import com.tdk.daggerdemo.second.ui.second.SecondFragment
import dagger.Subcomponent
import dagger.android.AndroidInjector


/**
 * @Author tangdekun
 * @Date 2018/7/30-10:53
 * @Email tangdekun0924@gmail.com
 */
@Subcomponent
@ActivityScope
interface MainComponent : AndroidInjector<MainActivity> {
    @Subcomponent.Factory
    interface Builder : AndroidInjector.Factory<MainActivity>
}


@Subcomponent
@ActivityScope
interface SecondActivityComponent : AndroidInjector<SecondActivity> {
    @Subcomponent.Factory
    interface Builder : AndroidInjector.Factory<SecondActivity>
}


@Subcomponent
@FragmentScope
interface SecondFragmentComponent : AndroidInjector<SecondFragment> {
    @Subcomponent.Factory
    interface Builder : AndroidInjector.Factory<SecondFragment>
}