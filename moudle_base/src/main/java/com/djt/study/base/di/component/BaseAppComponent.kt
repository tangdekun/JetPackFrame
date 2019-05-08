package com.djt.study.base.di.component

import com.djt.study.base.base.BaseApplication
import com.djt.study.base.di.builder.BaseActivityBuilder
import com.djt.study.base.di.builder.BaseBroadcastReceiverBuilder
import com.djt.study.base.di.builder.BaseServiceBuilder
import dagger.BindsInstance
import dagger.Component
import dagger.Subcomponent
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * @Author tangdekun
 * @Date 2018/8/4-15:52
 * @Email tangdekun0924@gmail.com
 */
@Singleton
@SupComponent(
    modules = [
        AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        BaseActivityBuilder::class,
        BaseServiceBuilder::class,
        BaseBroadcastReceiverBuilder::class
    ]
)
interface BaseAppComponent {

    fun inject(application: BaseApplication)

    @Subcomponent.Builder
    interface Builder {

        @BindsInstance
        fun application(application: BaseApplication): Builder

        fun build(): BaseAppComponent

    }

}