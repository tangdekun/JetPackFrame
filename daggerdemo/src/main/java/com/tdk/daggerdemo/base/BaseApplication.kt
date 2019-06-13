package com.tdk.daggerdemo.base

import com.tdk.daggerdemo.AppModule
import com.tdk.daggerdemo.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication


open class BaseApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).application(this).build()
        appComponent.inject(this)
        return appComponent
    }

    companion object {
        private const val TAG = "BaseApplication"
        lateinit var mContext: BaseApplication

    }

    open fun injectApp() {


    }


    override fun onCreate() {
        super.onCreate()
        mContext = this
        injectApp()

    }


}