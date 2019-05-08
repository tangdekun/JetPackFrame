package com.google.samples.apps.sunflower

import com.billy.cc.core.component.CC
import com.billy.cc.core.component.CCResult
import com.billy.cc.core.component.CCUtil
import com.billy.cc.core.component.IComponent

/**
 * @author billy.qi
 */
class ComponentFlower : IComponent {
    override fun getName(): String {
        return "ComponentFlower"
    }

    /**
     * 组件被调用时的入口
     * 要确保每个逻辑分支都会调用到CC.sendCCResult，
     * 包括try-catch,if-else,switch-case-default,startActivity
     * @param cc 组件调用对象，可从此对象中获取相关信息
     * @return true:将异步调用CC.sendCCResult(...),用于异步实现相关功能，例如：文件加载、网络请求等
     * false:会同步调用CC.sendCCResult(...),即在onCall方法return之前调用，否则将被视为不合法的实现
     */

    override fun onCall(cc: CC): Boolean {
        val actionName = cc.getActionName()
        when (actionName) {
            "showActivity" -> openActivity(cc)
            else -> {
            }
        }//这个逻辑分支上没有调用CC.sendCCResult(...),是一种错误的示例
        //并且方法的返回值为false，代表不会异步调用CC.sendCCResult(...)
        //在LocalCCInterceptor中将会返回错误码为-10的CCResult
        return false
    }

    private fun openActivity(cc: CC) {
        CCUtil.navigateTo(cc, GardenActivity::class.java)
        CC.sendCCResult(cc.getCallId(), CCResult.success())
    }
}
