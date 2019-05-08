package com.djt.base.http.interceptor.logging


import okhttp3.internal.platform.Platform

import java.util.logging.Level

/**
 * @author ihsan on 10/02/2017.
 */
internal open class I protected constructor() {

    init {
        throw UnsupportedOperationException()
    }

    companion object {

        fun log(type: Int, tag: String, msg: String) {
            val logger = java.util.logging.Logger.getLogger(tag)
            when (type) {
                Platform.INFO -> logger.log(Level.INFO, msg)
                else -> logger.log(Level.WARNING, msg)
            }
        }
    }
}
