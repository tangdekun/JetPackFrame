package debug

import com.billy.cc.core.component.CC
import com.djt.base.crash.CaocConfig
import com.djt.base.utils.KLog
import com.djt.study.demo.BuildConfig
import com.djt.study.demo.R
import com.djt.study.demo.ui.login.LoginActivity
import com.squareup.leakcanary.LeakCanary

/**
 * @author billy.qi
 * @since 17/11/20 20:02
 */
class DemoApp : BaseApplication() {
    @Override
    fun onCreate() {
        super.onCreate()
        CC.enableVerboseLog(true)
        CC.enableDebug(true)
        CC.enableRemoteCC(true)

        //是否开启打印日志
        KLog.init(BuildConfig.DEBUG)
        //初始化全局异常崩溃
        initCrash()
        //内存泄漏检测
        if (!LeakCanary.isInAnalyzerProcess(this)) {
            LeakCanary.install(this)
        }
    }


    private fun initCrash() {
        CaocConfig.Builder.create()
            .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT) //背景模式,开启沉浸式
            .enabled(true) //是否启动全局异常捕获
            .showErrorDetails(true) //是否显示错误详细信息
            .showRestartButton(true) //是否显示重启按钮
            .trackActivities(true) //是否跟踪Activity
            .minTimeBetweenCrashesMs(2000) //崩溃的间隔时间(毫秒)
            .errorDrawable(R.mipmap.demo_ic_launcher) //错误图标
            .restartActivity(LoginActivity::class.java) //重新启动后的activity
            //                .errorActivity(YourCustomErrorActivity.class) //崩溃后的错误activity
            //                .eventListener(new YourCustomEventListener()) //崩溃后的错误监听
            .apply()
    }
}
