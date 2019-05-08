package com.djt.study.base.base

import android.app.Application
import com.djt.base.utils.KLog
import com.djt.base.utils.Utils

/**
 * Created by goldze on 2018/6/21 0021.
 * 基础库自身初始化操作
 */

class BaseModuleInit : IModuleInit {

    override fun onInitAhead(application: Application): Boolean {
        //开启打印日志
        //初始化工具类
        Utils.init(application)
        KLog.init(true)
        KLog.e("基础层初始化 -- onInitAhead")
        return false
    }

    override fun onInitLow(application: Application): Boolean {
        KLog.e("基础层初始化 -- onInitLow")
        return false
    }
}
