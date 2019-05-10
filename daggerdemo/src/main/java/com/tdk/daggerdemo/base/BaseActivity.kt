package com.tdk.daggerdemo.base

import android.os.Bundle
import androidx.lifecycle.LifecycleRegistry
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import kotlin.reflect.KClass as KClass1

/**
 * @Author tangdekun
 * @Date 2018/7/23-20:15
 * @Email tangdekun0924@gmail.com
 */
abstract class BaseActivity : DaggerAppCompatActivity() {

    open lateinit var lifecycleRegistry: LifecycleRegistry


    override fun onCreate(savedInstanceState: Bundle?) {
        //一处依赖,处处引用
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        lifecycleRegistry = LifecycleRegistry(this)

    }


}