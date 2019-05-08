package com.djt.study.demo.data.source.local

import com.djt.study.demo.data.source.LocalDataSource
import com.djt.study.demo.data.source.http.service.DemoApiService
import com.djt.base.utils.SPUtils

/**
 * 本地数据源，可配合Room框架使用
 * Created by goldze on 2019/3/26.
 */
class LocalDataSourceImpl private constructor()//数据库Helper构建
    : LocalDataSource {
    private val apiService: DemoApiService? = null

    val userName: String
        @Override
        get() = SPUtils.getInstance().getString("UserName")

    val password: String
        @Override
        get() = SPUtils.getInstance().getString("password")

    @Override
    fun saveUserName(userName: String) {
        SPUtils.getInstance().put("UserName", userName)
    }

    @Override
    fun savePassword(password: String) {
        SPUtils.getInstance().put("password", password)
    }

    companion object {
        @Volatile
        private var INSTANCE: LocalDataSourceImpl? = null

        val instance: LocalDataSourceImpl?
            get() {
                if (INSTANCE == null) {
                    synchronized(LocalDataSourceImpl::class.java) {
                        if (INSTANCE == null) {
                            INSTANCE = LocalDataSourceImpl()
                        }
                    }
                }
                return INSTANCE
            }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}
