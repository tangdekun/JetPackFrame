package com.djt.study.base.di.builder

import com.djt.study.base.di.component.BaseActivityComponent
import dagger.Module

/**
 * @Author tangdekun
 * @Date 2018/7/26-19:29
 * @Email tangdekun0924@gmail.com
 */
@Module(subcomponents = [(BaseActivityComponent::class)])
abstract class BaseActivityBuilder