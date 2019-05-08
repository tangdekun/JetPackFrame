package com.djt.base.http


import io.reactivex.observers.DisposableObserver
import com.djt.base.AppManager
import com.djt.base.utils.KLog
import com.djt.base.utils.ToastUtils
import com.djt.base.utils.Utils
import java.util.*

/**
 * Created by goldze on 2017/5/10.
 * 统一的Code封装处理。该类仅供参考，实际业务逻辑, 根据需求来定义，
 */

abstract class ApiDisposableObserver<T> : DisposableObserver<T>() {
    abstract fun onResult(t: T)

    override fun onComplete() {

    }

    override fun onError(e: Throwable) {
        e.printStackTrace()
        KLog.e(e.message)
        if (e is ResponseThrowable) {
            val rError = e as ResponseThrowable
            rError.message?.let { ToastUtils.showShort(it) }
            return
        }
        //其他全部甩锅网络异常
        ToastUtils.showShort("网络异常")
    }

    override fun onStart() {
        super.onStart()
        ToastUtils.showShort("http is start")
        // if  NetworkAvailable no !   must to call onCompleted
        if (!NetworkUtil.isNetworkAvailable()) {
            ToastUtils.showShort("无网络，读取缓存数据")
            onComplete()
        }
    }


    override fun onNext(t: T) {
        val baseResponse = t as BaseResponse<*>
        when (baseResponse.code) {
            CodeRule.CODE_200 ->
                //请求成功, 正确的操作方式
                onResult(baseResponse.result as T)
            CodeRule.CODE_220 ->
                // 请求成功, 正确的操作方式, 并消息提示
                onResult(baseResponse.result as T)
            CodeRule.CODE_300 -> {
                //请求失败，不打印Message
                KLog.e("请求失败")
                ToastUtils.showShort("错误代码:", baseResponse.code)
            }
            CodeRule.CODE_330 ->
                //请求失败，打印Message
                ToastUtils.showShort("请求失败", baseResponse.message!!)
            CodeRule.CODE_500 ->
                //服务器内部异常
                ToastUtils.showShort("错误代码:", baseResponse.code)
            CodeRule.CODE_503 ->
                //参数为空
                KLog.e("参数为空")
            CodeRule.CODE_502 ->
                //没有数据
                KLog.e("没有数据")
            CodeRule.CODE_510 -> {
                //无效的Token，提示跳入登录页
                ToastUtils.showShort("token已过期，请重新登录")
                //关闭所有页面
                AppManager.appManager.finishAllActivity()
            }
            CodeRule.CODE_530 -> ToastUtils.showShort("请先登录")
            CodeRule.CODE_551 -> ToastUtils.showShort("错误代码:", baseResponse.code)
            else -> ToastUtils.showShort("错误代码:", baseResponse.code)
        }//跳入登录界面
        //*****该类仅供参考，实际业务Code, 根据需求来定义，******//
    }

    object CodeRule {
        //请求成功, 正确的操作方式
        internal val CODE_200 = 200
        //请求成功, 消息提示
        internal val CODE_220 = 220
        //请求失败，不打印Message
        internal val CODE_300 = 300
        //请求失败，打印Message
        internal val CODE_330 = 330
        //服务器内部异常
        internal val CODE_500 = 500
        //参数为空
        internal val CODE_503 = 503
        //没有数据
        internal val CODE_502 = 502
        //无效的Token
        internal val CODE_510 = 510
        //未登录
        internal val CODE_530 = 530
        //请求的操作异常终止：未知的页面类型
        internal val CODE_551 = 551
    }
}