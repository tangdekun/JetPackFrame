package com.djt.base.http

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import com.djt.base.http.download.DownLoadSubscriber
import com.djt.base.http.download.ProgressCallBack
import com.djt.base.http.interceptor.ProgressInterceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

import java.util.concurrent.TimeUnit

/**
 * Created by goldze on 2017/5/11.
 * 文件下载管理，封装一行代码实现下载
 */

class DownLoadManager private constructor() {

    init {
        buildNetWork()
    }

    //下载
    fun load(downUrl: String, callBack: ProgressCallBack<ResponseBody>) {
        retrofit!!.create(ApiService::class.java)
            .download(downUrl)
            .subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
            .observeOn(Schedulers.io()) //指定线程保存文件
            .doOnNext { responseBody -> callBack.saveFile(responseBody) }
            .observeOn(AndroidSchedulers.mainThread()) //在主线程中更新ui
            .subscribe(DownLoadSubscriber(callBack))
    }

    private fun buildNetWork() {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(ProgressInterceptor())
            .connectTimeout(20, TimeUnit.SECONDS)
            .build()

        retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(NetworkUtil.url)
            .build()
    }

    private interface ApiService {
        @Streaming
        @GET
        fun download(@Url url: String): Observable<ResponseBody>
    }

    companion object {
        private var instance: DownLoadManager? = null

        private var retrofit: Retrofit? = null

        /**
         * 单例模式
         *
         * @return DownLoadManager
         */
        fun getInstance(): DownLoadManager {
            if (instance == null) {
                instance = DownLoadManager()
            }
            return instance!!
        }
    }
}
