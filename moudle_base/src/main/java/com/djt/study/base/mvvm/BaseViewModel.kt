package com.djt.study.base.mvvm

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import com.djt.base.utils.KLog

/**
 * @Author tangdekun
 * @Date 2018/9/12-9:22
 * @Email tangdekun0924@gmail.com
 */
open class BaseViewModel : ViewModel(), LifecycleObserver {
    companion object {
        private val TAG = BaseViewModel::class.java.name
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    open fun onCreate() {
        KLog.d(TAG,"onCreate")
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    open fun onStart() {
        KLog.d(TAG,"onStart")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    open fun onResume() {
        KLog.d(TAG,"onResume")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    open fun onPause() {
        KLog.d(TAG,"onPause")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    open fun onStop() {
        KLog.d(TAG,"onStop")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    open fun onDestroy() {
        KLog.d(TAG,"onDestroy")
    }
}