package com.djt.study.base.di.component

import com.djt.study.base.base.BaseBroadcastReceiver
import dagger.Subcomponent
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector

/**
 * @Author tangdekun
 * @Date 2018/8/10-9:34
 * @Email tangdekun0924@gmail.com
 */
@Subcomponent(modules = [AndroidInjectionModule::class])
interface BaseBroadcastReceiverComponent : AndroidInjector<BaseBroadcastReceiver> {

    //每一个继承BaseBroadcastReceiver的BroadcastReceiver，都共享同一个SubComponent
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Factory<BaseBroadcastReceiver>
}