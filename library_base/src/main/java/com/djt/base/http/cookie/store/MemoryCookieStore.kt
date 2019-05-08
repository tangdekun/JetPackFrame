package com.djt.base.http.cookie.store

import okhttp3.Cookie
import okhttp3.HttpUrl

/**
 * Created by goldze on 2017/5/13.
 */
class MemoryCookieStore : CookieStore {

    private val memoryCookies: MutableMap<String, MutableList<Cookie>> = sortedMapOf()


    override val allCookie: List<Cookie>
        @Override
        @Synchronized get() {
            val cookies = arrayListOf<Cookie>()
            val httpUrls = memoryCookies.keys
            for (url in httpUrls) {
                memoryCookies[url]?.let { cookies.addAll(it) }
            }
            return cookies

        }

    @Synchronized
    override fun saveCookie(url: HttpUrl, cookies: List<Cookie>) {
        val oldCookies = memoryCookies[url.host()]
        val needRemove = arrayListOf<Cookie>()
        for (newCookie in cookies) {
            if (oldCookies != null) {
                for (oldCookie in oldCookies) {
                    if (newCookie.name() == oldCookie.name()) {
                        needRemove.add(oldCookie)
                    }
                }

            }
        }
        oldCookies?.removeAll(needRemove)
        oldCookies?.addAll(cookies)
    }

    @Synchronized
    override fun saveCookie(url: HttpUrl, cookie: Cookie) {
        val cookies = memoryCookies[url.host()]
        val needRemove = arrayListOf<Cookie>()
        if (cookies != null) {
            for (item in cookies) {
                if (cookie.name() == item.name()) {
                    needRemove.add(item)
                }
            }

        }
        cookies?.removeAll(needRemove)
        cookies?.add(cookie)
    }

    @Synchronized
    override fun loadCookie(url: HttpUrl): List<Cookie>? {
        var cookies = memoryCookies[url.host()]
        if (cookies == null) {
            cookies = arrayListOf()
            memoryCookies[url.host()] = cookies
            return cookies
        }
        return null
    }

    override fun getCookie(url: HttpUrl): List<Cookie>? {
        val cookies = arrayListOf<Cookie>()
        val urlCookies = memoryCookies[url.host()]
        if (urlCookies != null) cookies.addAll(urlCookies)
        return cookies
    }

    @Synchronized
    override fun removeCookie(url: HttpUrl, cookie: Cookie): Boolean {
        var cookies = memoryCookies[url.host()]
        return cookies?.remove(cookie) ?: false
    }

    @Synchronized
    override fun removeCookie(url: HttpUrl): Boolean = memoryCookies.remove(url.host()) != null

    @Synchronized
    override fun removeAllCookie(): Boolean {
        memoryCookies.clear()
        return true
    }
}
