package com.djt.base.utils

/**
 * @Author tangdekun
 * @Date 2018/8/10-14:09
 * @Email tangdekun0924@gmail.com
 */

import android.app.ActivityManager
import android.content.Context

/**工具类
 *
 * Created by jianddongguo on 2017/7/10.
 * http://blog.csdn.net/andrexpert
 */

object SystemUtils {

    /**
     * 判断本应用是否存活
     * 如果需要判断本应用是否在后台还是前台用getRunningTask
     */
    fun isAPPALive(mContext: Context, packageName: String): Boolean {
        var isAPPRunning = false
        // 获取activity管理对象
        val activityManager = mContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        // 获取所有正在运行的app
        val appProcessInfoList = activityManager.runningAppProcesses
        // 遍历，进程名即包名
        for (appInfo in appProcessInfoList) {
            if (packageName == appInfo.processName) {
                isAPPRunning = true
                break
            }
        }
        return isAPPRunning
    }


}