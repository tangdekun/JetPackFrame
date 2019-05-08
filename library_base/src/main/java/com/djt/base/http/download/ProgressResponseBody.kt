package com.djt.base.http.download

import com.djt.base.bus.RxBus
import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.*

import java.io.IOException

/**
 * Created by goldze on 2017/5/11.
 */

class ProgressResponseBody(private val responseBody: ResponseBody, val tag: String = "") : ResponseBody() {
    private var bufferedSource: BufferedSource? = null

    override fun contentType(): MediaType? {
        return responseBody.contentType()
    }

    override fun contentLength(): Long {
        return responseBody.contentLength()
    }

    override fun source(): BufferedSource? {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(source(responseBody.source()))
        }
        return bufferedSource
    }

    private fun source(source: Source): Source {

        return object : ForwardingSource(source) {
            var bytesReaded: Long = 0

            @Throws(IOException::class)
            override fun read(sink: Buffer, byteCount: Long): Long {
                val bytesRead = super.read(sink, byteCount)
                bytesReaded += if (bytesRead == -1L) 0L else bytesRead
                //使用RxBus的方式，实时发送当前已读取(上传/下载)的字节数据
                RxBus.default.post(DownLoadStateBean(contentLength(), bytesReaded, tag))
                return bytesRead
            }
        }
    }
}
