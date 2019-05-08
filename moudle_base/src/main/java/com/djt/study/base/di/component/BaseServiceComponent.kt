package com.djt.study.base.di.component

import com.djt.study.base.base.BaseService
import dagger.Subcomponent
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector

/**
 * @Author tangdekun
 * @Date 2018/8/10-9:30
 * @Email tangdekun0924@gmail.com
 */
@Subcomponent(modules = [(AndroidInjectionModule::class)])
interface BaseServiceComponent : AndroidInjector<BaseService> {
    //每一个继承BaseService的Service，都共享同一个SubComponent
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Factory<BaseService>
}