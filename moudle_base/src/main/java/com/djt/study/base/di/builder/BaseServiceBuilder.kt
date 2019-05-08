package com.djt.study.base.di.builder

import com.djt.study.base.di.component.BaseServiceComponent
import dagger.Module


/**
 * @Author tangdekun
 * @Date 2018/8/10-17:27
 * @Email tangdekun0924@gmail.com
 */
@Module(subcomponents = [(BaseServiceComponent::class)])
abstract class BaseServiceBuilder