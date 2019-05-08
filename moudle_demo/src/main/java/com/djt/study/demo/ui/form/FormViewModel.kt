package com.djt.study.demo.ui.form

import android.app.Application
import android.databinding.ObservableBoolean
import android.text.TextUtils
import android.view.View
import com.djt.study.demo.entity.FormEntity
import com.djt.study.demo.entity.SpinnerItemData
import com.djt.study.demo.ui.base.viewmodel.ToolbarViewModel
import com.google.gson.Gson
import com.djt.mvvm.binding.command.BindingAction
import com.djt.mvvm.binding.command.BindingCommand
import com.djt.mvvm.binding.command.BindingConsumer
import com.djt.mvvm.binding.viewadapter.spinner.IKeyAndValue
import com.djt.base.bus.event.SingleLiveEvent
import com.djt.base.utils.ToastUtils

import java.util.ArrayList

/**
 * Created by goldze on 2017/7/17.
 */

class FormViewModel(@NonNull application: Application) : ToolbarViewModel(application) {
    var entity: FormEntity? = null

    var sexItemDatas: List<IKeyAndValue>
    var entityJsonLiveData: SingleLiveEvent<String> = SingleLiveEvent()
    //封装一个界面发生改变的观察者
    var uc: UIChangeObservable

    //性别选择的监听
    var onSexSelectorCommand: BindingCommand<IKeyAndValue> = BindingCommand(object : BindingConsumer<IKeyAndValue>() {
        @Override
        fun call(iKeyAndValue: IKeyAndValue) {
            entity!!.setSex(iKeyAndValue.getValue())
        }
    })
    //生日选择的监听
    var onBirClickCommand = BindingCommand(object : BindingAction() {
        @Override
        fun call() {
            //回调到view层(Fragment)中显示日期对话框
            uc.showDateDialogObservable.set(!uc.showDateDialogObservable.get())
        }
    })
    //是否已婚Switch点状态改变回调
    var onMarryCheckedChangeCommand: BindingCommand<Boolean> = BindingCommand(object : BindingConsumer<Boolean>() {
        @Override
        fun call(isChecked: Boolean) {
            entity!!.setMarry(isChecked)
        }
    })
    //提交按钮点击事件
    var onCmtClickCommand = BindingCommand(object : BindingAction() {
        @Override
        fun call() {
            val submitJson = Gson().toJson(entity)
            entityJsonLiveData.setValue(submitJson)
        }
    })

    inner class UIChangeObservable {
        //显示日期对话框
        var showDateDialogObservable: ObservableBoolean

        init {
            showDateDialogObservable = ObservableBoolean(false)
        }
    }

    @Override
    fun onCreate() {
        super.onCreate()
        uc = UIChangeObservable()
        //sexItemDatas 一般可以从本地Sqlite数据库中取出数据字典对象集合，让该对象实现IKeyAndValue接口
        sexItemDatas = ArrayList()
        sexItemDatas.add(SpinnerItemData("男", "1"))
        sexItemDatas.add(SpinnerItemData("女", "2"))
    }

    /**
     * 初始化Toolbar
     */
    fun initToolbar() {
        //初始化标题栏
        setRightTextVisible(View.VISIBLE)
        if (TextUtils.isEmpty(entity!!.getId())) {
            //ID为空是新增
            setTitleText("表单提交")
        } else {
            //ID不为空是修改
            setTitleText("表单编辑")
        }
    }

    @Override
    fun rightTextOnClick() {
        ToastUtils.showShort("更多")
    }

    fun setFormEntity(entity: FormEntity) {
        if (this.entity == null) {
            this.entity = entity
        }
    }

    fun setBir(year: Int, month: Int, dayOfMonth: Int) {
        //设置数据到实体中，自动刷新界面
        entity!!.setBir(year.toString() + "年" + (month + 1) + "月" + dayOfMonth + "日")
        //刷新实体,驱动界面更新
        entity!!.notifyChange()
    }

    @Override
    fun onDestroy() {
        super.onDestroy()
    }
}
