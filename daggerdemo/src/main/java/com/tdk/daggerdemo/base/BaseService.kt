package com.djt.study.base.base

import android.app.Service
import dagger.android.AndroidInjection

/**
 * @Author tangdekun
 * @Date 2018/8/10-9:24
 * @Email tangdekun0924@gmail.com
 */
abstract class BaseService : Service() {


    override fun onCreate() {
        AndroidInjection.inject(this)
        super.onCreate()

    }
}