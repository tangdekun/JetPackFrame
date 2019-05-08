package com.djt.study.base.base

import android.app.Application

/**
 * Created by goldze on 2018/6/21 0021.
 * 动态配置Application，有需要初始化的组件实现该接口，统一在主app的Application中初始化
 */

interface IModuleInit {
    /**初始化优先的
     *
     * @param application
     * @return
     */
    fun onInitAhead(application: Application): Boolean

    /**初始化靠后的
     *
     * @param application
     * @return
     */
    fun onInitLow(application: Application): Boolean
}
