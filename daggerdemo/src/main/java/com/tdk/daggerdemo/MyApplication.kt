package com.tdk.daggerdemo

import android.app.Activity
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.SharedPreferences.Editor
import androidx.multidex.MultiDex
import com.blankj.utilcode.util.Utils
import com.djt.base.utils.Utils
import dagger.android.*
import javax.inject.Inject

/**
 * @Author tangdekun
 * @Date 2018/7/30-14:50
 * @Email tangdekun0924@gmail.com
 */
class MyApplication : DaggerApplication(){


    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val mAppComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .application(this)
                .build()
        AppComponentHolder.mAppComponent = mAppComponent
        return mAppComponent
    }


    @Inject
    lateinit var mSharedPreferencesEditor: Editor

    override fun onCreate() {
        super.onCreate()
        Utils.init(this)
        mSharedPreferencesEditor.putString("test", "test")
    }


}