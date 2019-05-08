package com.djt.study.demo.utils

import android.content.Context
import android.text.TextUtils
import com.djt.study.demo.BuildConfig
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import com.djt.base.http.cookie.CookieJarImpl
import com.djt.base.http.cookie.store.PersistentCookieStore
import com.djt.base.http.interceptor.BaseInterceptor
import com.djt.base.http.interceptor.CacheInterceptor
import com.djt.base.http.interceptor.logging.Level
import com.djt.base.http.interceptor.logging.LoggingInterceptor
import com.djt.base.utils.KLog
import com.djt.base.utils.Utils
import okhttp3.Cache
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Created by goldze on 2017/5/10.
 * RetrofitClient封装单例类, 实现网络请求
 */
class RetrofitClient private constructor(url: String = baseUrl, headers: Map<String, String>? = null) {

    private var cache: Cache? = null
    private var httpCacheDirectory: File? = null

    private object SingletonHolder {
        private val INSTANCE = RetrofitClient()
    }

    init {
        var url = url

        if (TextUtils.isEmpty(url)) {
            url = baseUrl
        }

        if (httpCacheDirectory == null) {
            httpCacheDirectory = File(mContext.getCacheDir(), "goldze_cache")
        }

        try {
            if (cache == null) {
                cache = Cache(httpCacheDirectory, CACHE_TIMEOUT)
            }
        } catch (e: Exception) {
            KLog.e("Could not create http cache", e)
        }

        val sslParams = HttpsUtils.getSslSocketFactory()
        okHttpClient = OkHttpClient.Builder()
            .cookieJar(CookieJarImpl(PersistentCookieStore(mContext)))
            //                .cache(cache)
            .addInterceptor(BaseInterceptor(headers))
            .addInterceptor(CacheInterceptor(mContext))
            .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
            .addInterceptor(
                LoggingInterceptor.Builder()//构建者模式
                    .loggable(BuildConfig.DEBUG) //是否开启日志打印
                    .setLevel(Level.BASIC) //打印的等级
                    .log(Platform.INFO) // 打印类型
                    .request("Request") // request的Tag
                    .response("Response")// Response的Tag
                    .addHeader("log-header", "I am the log request header.") // 添加打印头, 注意 key 和 value 都不能是中文
                    .build()
            )
            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .connectionPool(ConnectionPool(8, 15, TimeUnit.SECONDS))
            // 这里你可以根据自己的机型设置同时连接的个数和时间，我这里8个，和每个保持时间为10s
            .build()
        retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(url)
            .build()

    }

    /**
     * create you ApiService
     * Create an implementation of the API endpoints defined by the `service` interface.
     */
    fun <T> create(service: Class<T>?): T {
        if (service == null) {
            throw RuntimeException("Api service is null!")
        }
        return retrofit.create(service)
    }

    companion object {
        //超时时间
        private val DEFAULT_TIMEOUT = 20
        //缓存时间
        private val CACHE_TIMEOUT = 10 * 1024 * 1024
        //服务端根路径
        var baseUrl = "https://www.oschina.net/"

        private val mContext = Utils.getContext()

        private var okHttpClient: OkHttpClient
        private var retrofit: Retrofit

        val instance: RetrofitClient
            get() = SingletonHolder.INSTANCE

/**
 * /**
 * execute your customer API
 * For example:
 * MyApiService service =
 * RetrofitClient.getInstance(MainActivity.this).create(MyApiService.class);
 *
 *
 * RetrofitClient.getInstance(MainActivity.this)
 * .execute(service.lgon("name", "password"), subscriber)
 * * @param subscriber
*/

fun <T> execute(observable:Observable<T>, subscriber:Observer<T>):T? {
observable.subscribeOn(Schedulers.io())
.unsubscribeOn(Schedulers.io())
.observeOn(AndroidSchedulers.mainThread())
.subscribe(subscriber)

return null
}
}
}
