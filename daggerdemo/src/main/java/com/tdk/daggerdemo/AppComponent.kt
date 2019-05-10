package com.tdk.daggerdemo

import com.tdk.daggerdemo.base.BaseApplication
import com.tdk.daggerdemo.di.module.ActivityModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * @Author tangdekun
 * @Date 2018/7/30-14:51
 * @Email tangdekun0924@gmail.com
 */
@Singleton
@Component(
    modules = [
        AppModule::class,
        AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        ActivityModule::class,
        MainModule::class
    ]
)
interface AppComponent : AndroidInjector<MyApplication> {


    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: BaseApplication): Builder

        fun build(): AppComponent

        fun appModule(appModule: AppModule): Builder

    }
}