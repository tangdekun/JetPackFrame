package com.djt.study.demo.ui.main

import android.Manifest
import android.app.ProgressDialog
import android.arch.lifecycle.Observer
import android.content.pm.ActivityInfo
import android.os.Bundle

import com.djt.study.demo.R
import com.djt.study.demo.databinding.DemoActivityDemoBinding
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.functions.Consumer
import com.djt.mvvm.base.BaseActivity
import com.djt.base.http.DownLoadManager
import com.djt.base.http.download.ProgressCallBack
import com.djt.base.utils.ToastUtils
import okhttp3.ResponseBody
import com.djt.mvvm.BR

/**
 * Created by goldze on 2017/7/17.
 */

class DemoActivity : BaseActivity<DemoActivityDemoBinding, DemoViewModel>() {
    @Override
    fun initParam() {
        super.initParam()
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
    }

    @Override
    fun initContentView(savedInstanceState: Bundle): Int {
        return R.layout.demo_activity_demo
    }

    @Override
    fun initVariableId(): Int {
        return BR.viewModel
    }

    @Override
    fun initViewObservable() {
        //注册监听相机权限的请求
        viewModel.requestCameraPermissions.observe(this, object : Observer<Boolean>() {
            @Override
            fun onChanged(@Nullable aBoolean: Boolean) {
                requestCameraPermissions()
            }
        })
        //注册文件下载的监听
        viewModel.loadUrlEvent.observe(this, object : Observer<String>() {
            @Override
            fun onChanged(@Nullable url: String) {
                downFile(url)
            }
        })
    }

    /**
     * 请求相机权限
     */
    private fun requestCameraPermissions() {
        //请求打开相机权限
        val rxPermissions = RxPermissions(this@DemoActivity)
        rxPermissions.request(Manifest.permission.CAMERA)
            .subscribe(object : Consumer<Boolean>() {
                @Override
                @Throws(Exception::class)
                fun accept(aBoolean: Boolean) {
                    if (aBoolean) {
                        ToastUtils.showShort("相机权限已经打开，直接跳入相机")
                    } else {
                        ToastUtils.showShort("权限被拒绝")
                    }
                }
            })
    }

    private fun downFile(url: String) {
        val destFileDir = getApplication().getCacheDir().getPath()
        val destFileName = System.currentTimeMillis() + ".apk"
        val progressDialog = ProgressDialog(this)
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
        progressDialog.setTitle("正在下载...")
        progressDialog.setCancelable(false)
        progressDialog.show()
        DownLoadManager.getInstance().load(url, object : ProgressCallBack<ResponseBody>(destFileDir, destFileName) {
            @Override
            fun onStart() {
                super.onStart()
            }

            @Override
            fun onCompleted() {
                progressDialog.dismiss()
            }

            @Override
            fun onSuccess(responseBody: ResponseBody) {
                ToastUtils.showShort("文件下载完成！")
            }

            @Override
            fun progress(progress: Long, total: Long) {
                progressDialog.setMax(total.toInt())
                progressDialog.setProgress(progress.toInt())
            }

            @Override
            fun onError(e: Throwable) {
                e.printStackTrace()
                ToastUtils.showShort("文件下载失败！")
                progressDialog.dismiss()
            }
        })
    }
}
