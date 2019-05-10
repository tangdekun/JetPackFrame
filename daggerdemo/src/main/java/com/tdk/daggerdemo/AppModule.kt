package com.tdk.daggerdemo

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import com.tdk.daggerdemo.base.BaseApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @Author tangdekun
 * @Date 2018/7/30-14:20
 * @Email tangdekun0924@gmail.com
 */
@Module
class AppModule(private val application: BaseApplication) {

    @Singleton
    @Provides
    fun provideAppContext(): Context {
        return application
    }

    @Singleton
    @Provides
    fun provideSharedPreference(): SharedPreferences {
        return application.getSharedPreferences(Constant.SHAREDPREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideSharedPreferencesEdit(mSharedPreferences: SharedPreferences): Editor {
        return mSharedPreferences.edit()
    }

}