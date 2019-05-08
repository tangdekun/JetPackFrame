package com.djt.base.utils

import android.content.Context
import androidx.fragment.app.Fragment
import com.djt.base.http.BaseResponse
import com.djt.base.http.ExceptionHandle
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

/**
 * Created by goldze on 2017/6/19.
 * 有关Rx的工具类
 */
object RxUtils {
    /**
     * 生命周期绑定
     *
     * @param lifecycle Activity
     */
    fun <T> bindToLifecycle(lifecycle: Context): LifecycleTransformer<T> {
        return if (lifecycle is LifecycleProvider<*>) {
            (lifecycle as LifecycleProvider<*>).bindToLifecycle()
        } else {
            throw IllegalArgumentException("context not the LifecycleProvider type")
        }
    }

    /**
     * 生命周期绑定
     *
     * @param lifecycle Fragment
     */
    fun bindToLifecycle(lifecycle: Fragment): LifecycleTransformer<Any> {
        return if (lifecycle is LifecycleProvider<*>) {
            (lifecycle as LifecycleProvider<*>).bindToLifecycle()
        } else {
            throw IllegalArgumentException("fragment not the LifecycleProvider type")
        }
    }

    /**
     * 生命周期绑定
     *
     * @param lifecycle Fragment
     */
    fun bindToLifecycle(lifecycle: LifecycleProvider<Any>): LifecycleTransformer<Any> {
        return lifecycle.bindToLifecycle()
    }

    /**
     * 线程调度器
     */
    fun schedulersTransformer(): ObservableTransformer<Any, Any> {
        return object : ObservableTransformer<Any, Any> {
            override fun apply(upstream: Observable<Any>): ObservableSource<Any> {
                return upstream.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
            }
        }
    }

    fun exceptionTransformer(): ObservableTransformer<Any, Any> {

        return object : ObservableTransformer<Any, Any> {
            override fun apply(observable: Observable<Any>): ObservableSource<Any> {
                return observable
                    //                        .map(new HandleFuc<T>())  //这里可以取出BaseResponse中的Result
                    .onErrorResumeNext(HttpResponseFunc())
            }
        }
    }

    private class HttpResponseFunc<T> : Function<Throwable, Observable<T>> {
        override fun apply(t: Throwable): Observable<T> {
            return Observable.error(ExceptionHandle.handleException(t))
        }
    }

    private class HandleFuc<T> : Function<BaseResponse<T>, T> {
        override fun apply(response: BaseResponse<T>): T {
            if (!response.isOk) {
                if ("" != "${response.code}${response.message}") {
                    throw RuntimeException("${response.code}${response.message}")
                } else {
                    throw RuntimeException("")
                }

            }

            return response.result!!
        }
    }

}
