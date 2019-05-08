package com.djt.base.http.download

import android.util.Log
import com.djt.base.bus.RxBus
import com.djt.base.bus.RxSubscriptions
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import okhttp3.ResponseBody

import java.io.*

/**
 * Created by goldze on 2017/9/26 0026.
 *
 */

abstract class ProgressCallBack<T>(
    private val destFileDir: String // 本地文件存放路径
    , private val destFileName: String // 文件名
) {
    private var mSubscription: Disposable? = null

    init {
        subscribeLoadProgress()
    }

    abstract fun onSuccess(t: T)

    abstract fun progress(progress: Long, total: Long)

    fun onStart() {}

    fun onCompleted() {}

    abstract fun onError(e: Throwable)

    fun saveFile(body: ResponseBody) {
        var input: InputStream? = null
        val buf = ByteArray(2048)
        var len: Int
        var fos: FileOutputStream? = null
        try {
            input = body.byteStream()
            val dir = File(destFileDir)
            if (!dir.exists()) {
                dir.mkdirs()
            }
            val file = File(dir, destFileName)
            fos = file.outputStream()
            fos.write(input.readBytes())
            while ((input.read(buf).also { len = it }) != -1) {
                fos.write(buf, 0, len)
            }
            fos.flush()
            unsubscribe()

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                input?.close()
                fos?.close()
            } catch (e: IOException) {
                Log.e("saveFile", e.message)
            }

        }
    }

    /**
     * 订阅加载的进度条
     */
    fun subscribeLoadProgress() {
        mSubscription = RxBus.default.toObservable(DownLoadStateBean::class.java)
            .observeOn(AndroidSchedulers.mainThread()) //回调到主线程更新UI
            .subscribe { progressLoadBean -> progress(progressLoadBean.bytesLoaded, progressLoadBean.total) }
        //将订阅者加入管理站
        RxSubscriptions.add(mSubscription)
    }

    /**
     * 取消订阅，防止内存泄漏
     */
    fun unsubscribe() {
        RxSubscriptions.remove(mSubscription)
    }
}