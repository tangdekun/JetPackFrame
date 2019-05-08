package com.djt.study.base.di.builder

import com.djt.study.base.di.component.BaseBroadcastReceiverComponent
import dagger.Module

/**
 * @Author tangdekun
 * @Date 2018/8/10-17:27
 * @Email tangdekun0924@gmail.com
 */
@Module(subcomponents = [(BaseBroadcastReceiverComponent::class)])
abstract class BaseBroadcastReceiverBuilder