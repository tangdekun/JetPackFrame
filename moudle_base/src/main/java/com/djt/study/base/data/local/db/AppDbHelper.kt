package com.djt.study.base.data.local.db


import javax.inject.Inject
import javax.inject.Singleton

/**
 * @Author tangdekun
 * @Date 2018/8/2-15:16
 * @Email tangdekun0924@gmail.com
 */
@Singleton
open class AppDbHelper @Inject constructor(appDataBase: AppDataBase) : DbHelper {

    private val mAppDataBase: AppDataBase = appDataBase



}