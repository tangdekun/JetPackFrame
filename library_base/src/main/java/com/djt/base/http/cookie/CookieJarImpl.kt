package com.djt.base.http.cookie


import com.djt.base.http.cookie.store.CookieStore
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

/**
 * Created by goldze on 2017/5/13.
 */
class CookieJarImpl(private val cookieStore: CookieStore) : CookieJar {

    init {

    }

    @Synchronized
    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        cookieStore.saveCookie(url, cookies)
    }

    @Synchronized
    override fun loadForRequest(url: HttpUrl): List<Cookie>? {
        return cookieStore.loadCookie(url)
    }
}