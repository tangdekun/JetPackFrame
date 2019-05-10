package com.tdk.daggerdemo

import android.content.Context
import android.content.SharedPreferences.Editor
import androidx.multidex.MultiDex
import com.djt.base.utils.Utils
import com.tdk.daggerdemo.base.BaseApplication
import javax.inject.Inject

/**
 * @Author tangdekun
 * @Date 2018/7/30-14:50
 * @Email tangdekun0924@gmail.com
 */
class MyApplication : BaseApplication(){


    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }


    @Inject
    lateinit var mSharedPreferencesEditor: Editor

    override fun onCreate() {
        super.onCreate()
        Utils.init(this)
        mSharedPreferencesEditor.putString("test", "test")
    }


}