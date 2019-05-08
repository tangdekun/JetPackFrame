package com.djt.base.http.interceptor.logging

import okhttp3.internal.platform.Platform

/**
 * @author ihsan on 11/07/2017.
 */
@SuppressWarnings("WeakerAccess", "unused")
interface Logger {
    fun log(level: Int, tag: String, msg: String)

    companion object {

        val DEFAULT: Logger = object : Logger {
            @Override
            override fun log(level: Int, tag: String, message: String) {
                Platform.get().log(level, message, null)
            }
        }
    }
}
