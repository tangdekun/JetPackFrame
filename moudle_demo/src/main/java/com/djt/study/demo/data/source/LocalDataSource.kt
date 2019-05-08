package com.djt.study.demo.data.source

/**
 * Created by goldze on 2019/3/26.
 */
interface LocalDataSource {

    /**
     * 获取用户名
     */
    val userName: String

    /**
     * 获取用户密码
     */
    val password: String

    /**
     * 保存用户名
     */
    fun saveUserName(userName: String)

    /**
     * 保存用户密码
     */

    fun savePassword(password: String)
}
