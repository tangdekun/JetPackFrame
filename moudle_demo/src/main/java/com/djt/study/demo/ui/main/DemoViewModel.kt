package com.djt.study.demo.ui.main

import android.app.Application
import android.os.Bundle
import com.djt.study.demo.entity.FormEntity
import com.djt.study.demo.ui.form.FormFragment
import com.djt.study.demo.ui.network.NetWorkFragment
import com.djt.study.demo.ui.rv_multi.MultiRecycleViewFragment
import com.djt.study.demo.ui.tab_bar.activity.TabBarActivity
import com.djt.study.demo.ui.viewpager.activity.ViewPagerActivity
import com.djt.study.demo.ui.vp_frg.ViewPagerGroupFragment
import com.djt.mvvm.base.BaseViewModel
import com.djt.mvvm.binding.command.BindingAction
import com.djt.mvvm.binding.command.BindingCommand
import com.djt.base.bus.event.SingleLiveEvent

/**
 * Created by goldze on 2017/7/17.
 */

class DemoViewModel(@NonNull application: Application) : BaseViewModel(application) {
    //使用Observable
    var requestCameraPermissions: SingleLiveEvent<Boolean> = SingleLiveEvent()
    //使用LiveData
    var loadUrlEvent: SingleLiveEvent<String> = SingleLiveEvent()

    //网络访问点击事件
    var netWorkClick = BindingCommand(object : BindingAction() {
        @Override
        fun call() {
            startContainerActivity(NetWorkFragment::class.java!!.getCanonicalName())
        }
    })
    //RecycleView多布局
    var rvMultiClick = BindingCommand(object : BindingAction() {
        @Override
        fun call() {
            startContainerActivity(MultiRecycleViewFragment::class.java!!.getCanonicalName())
        }
    })
    //进入TabBarActivity
    var startTabBarClick = BindingCommand(object : BindingAction() {
        @Override
        fun call() {
            startActivity(TabBarActivity::class.java)
        }
    })
    //ViewPager绑定
    var viewPagerBindingClick = BindingCommand(object : BindingAction() {
        @Override
        fun call() {
            startActivity(ViewPagerActivity::class.java)
        }
    })
    //ViewPager+Fragment
    var viewPagerGroupBindingClick = BindingCommand(object : BindingAction() {
        @Override
        fun call() {
            startContainerActivity(ViewPagerGroupFragment::class.java!!.getCanonicalName())
        }
    })
    //表单提交点击事件
    var formSbmClick = BindingCommand(object : BindingAction() {
        @Override
        fun call() {
            startContainerActivity(FormFragment::class.java!!.getCanonicalName())
        }
    })
    //表单修改点击事件
    var formModifyClick = BindingCommand(object : BindingAction() {
        @Override
        fun call() {
            //模拟一个修改的实体数据
            val entity = FormEntity()
            entity.setId("12345678")
            entity.setName("goldze")
            entity.setSex("1")
            entity.setBir("xxxx年xx月xx日")
            entity.setMarry(true)
            //传入实体数据
            val mBundle = Bundle()
            mBundle.putParcelable("entity", entity)
            startContainerActivity(FormFragment::class.java!!.getCanonicalName(), mBundle)
        }
    })
    //权限申请
    var permissionsClick = BindingCommand(object : BindingAction() {
        @Override
        fun call() {
            requestCameraPermissions.call()
        }
    })

    //全局异常捕获
    var exceptionClick = BindingCommand(object : BindingAction() {
        @Override
        fun call() {
            //伪造一个异常
            Integer.parseInt("goldze")
        }
    })
    //文件下载
    var fileDownLoadClick = BindingCommand(object : BindingAction() {
        @Override
        fun call() {
            loadUrlEvent.setValue("http://gdown.baidu.com/data/wisegame/a2cd8828b227b9f9/neihanduanzi_692.apk")
        }
    })
}
