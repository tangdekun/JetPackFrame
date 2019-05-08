package com.djt.study.demo.app

import android.annotation.SuppressLint
import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.support.annotation.VisibleForTesting
import com.djt.study.demo.data.DemoRepository
import com.djt.study.demo.ui.login.LoginViewModel
import com.djt.study.demo.ui.network.NetWorkViewModel

/**
 * Created by goldze on 2019/3/26.
 */
class AppViewModelFactory private constructor(
    private val mApplication: Application,
    private val mRepository: DemoRepository
) : ViewModelProvider.NewInstanceFactory() {

    @NonNull
    @Override
    fun <T : ViewModel> create(@NonNull modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NetWorkViewModel::class.java)) {
            return NetWorkViewModel(mApplication, mRepository) as T
        } else if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(mApplication, mRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName())
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: AppViewModelFactory? = null

        fun getInstance(application: Application): AppViewModelFactory? {
            if (INSTANCE == null) {
                synchronized(AppViewModelFactory::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = AppViewModelFactory(application, Injection.provideDemoRepository())
                    }
                }
            }
            return INSTANCE
        }

        @VisibleForTesting
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}
