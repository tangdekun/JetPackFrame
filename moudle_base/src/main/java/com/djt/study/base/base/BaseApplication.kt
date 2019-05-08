package com.djt.study.base.base

import android.app.Activity
import android.app.Service
import android.content.BroadcastReceiver
import androidx.multidex.MultiDexApplication
import com.djt.study.base.config.ModuleLifecycleConfig
import dagger.android.*
import dagger.android.support.DaggerApplication
import javax.inject.Inject


open class BaseApplication : MultiDexApplication(), HasActivityInjector, HasServiceInjector,
    HasBroadcastReceiverInjector {


    override fun serviceInjector(): DispatchingAndroidInjector<Service>? {
        return mServiceInjector
    }

    override fun broadcastReceiverInjector(): DispatchingAndroidInjector<BroadcastReceiver>? {
        return mBroadcastReceiverInjector
    }

    companion object {
        private const val TAG = "BaseApplication"
        lateinit var mContext: BaseApplication

    }

    @Inject
    @JvmField
    var mActivityInjector: DispatchingAndroidInjector<Activity>? = null

    @Inject
    @JvmField
    var mServiceInjector: DispatchingAndroidInjector<Service>? = null

    @Inject
    @JvmField
    var mBroadcastReceiverInjector: DispatchingAndroidInjector<BroadcastReceiver>? = null

    override fun activityInjector(): DispatchingAndroidInjector<Activity>? {
        return mActivityInjector
    }

    open fun injectApp() {
        ModuleLifecycleConfig.instance.initModuleAhead(this)

        ModuleLifecycleConfig.instance.initModuleLow(this)

    }


    override fun onCreate() {
        super.onCreate()
        mContext = this
        injectApp()

    }


}