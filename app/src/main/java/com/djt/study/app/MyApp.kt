package com.djt.study.app

import android.app.Application
import com.billy.cc.core.component.CC

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        CC.enableVerboseLog(true)
        CC.enableDebug(true)
        CC.enableRemoteCC(true)
    }
}
