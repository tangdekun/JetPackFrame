package com.djt.study.base.di.module

import android.content.Context
import androidx.room.Room
import com.djt.study.base.base.AppConstants
import com.djt.study.base.base.BaseApplication
import com.djt.study.base.data.AppDataManager
import com.djt.study.base.data.DataManager
import com.djt.study.base.data.local.db.AppDataBase
import com.djt.study.base.data.local.db.DbHelper
import com.djt.study.base.data.local.sharedpreference.AppPreferencesHelper
import com.djt.study.base.data.local.sharedpreference.PreferencesHelper
import com.djt.study.base.data.remote.ApiHelper
import com.djt.study.base.data.remote.AppApiHelper
import com.djt.study.base.di.annotation.DatabaseInfo
import com.djt.study.base.di.annotation.PreferenceInfo
import com.google.gson.Gson
import com.djt.study.base.data.local.db.AppDbHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @Author tangdekun
 * @Date 2018/7/30-14:18
 * @Email tangdekun0924@gmail.com
 */
@Module
class AppModule {


    @Singleton
    @Provides
    fun provideAppContext(application: BaseApplication): Context {
        return application
    }

    @Singleton
    @Provides
    fun provideApiHelper(mAppApiHelper: AppApiHelper): ApiHelper {
        return mAppApiHelper
    }

    @Singleton
    @Provides
    fun provideDbHelper(mDbHelper: AppDbHelper): DbHelper {
        return mDbHelper
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return Gson()
    }

    @Singleton
    @Provides
    fun provideAppDatabase(@DatabaseInfo databaseName: String, context: Context): AppDataBase {
        return Room.databaseBuilder(context, AppDataBase::class.java, databaseName).fallbackToDestructiveMigration()
                .build()
    }

    @DatabaseInfo
    @Provides
    fun provideDatabaseName(): String {
        return AppConstants.DB_NAME
    }

    @Singleton
    @Provides
    fun providePreferencesHelper(appPreferencesHelper: AppPreferencesHelper): PreferencesHelper {
        return appPreferencesHelper
    }

    @PreferenceInfo
    @Provides
    fun provideSharedPreferencesFileName(): String {
        return AppConstants.PREF_NAME
    }

    @Singleton
    @Provides
    fun provideDataManager(mAppDataManager: AppDataManager): DataManager {
        return mAppDataManager
    }
}