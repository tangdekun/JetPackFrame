package com.djt.study.demo.ui.form

import android.app.DatePickerDialog
import android.arch.lifecycle.Observer
import android.databinding.Observable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.DatePicker

import com.djt.study.demo.R
import com.djt.study.demo.databinding.DemoFragmentFormBinding
import com.djt.study.demo.entity.FormEntity
import com.djt.mvvm.base.BaseFragment
import com.djt.base.utils.MaterialDialogUtils
import com.djt.mvvm.BR
import java.util.Calendar

/**
 * Created by goldze on 2017/7/17.
 * 表单提交/编辑界面
 */

class FormFragment : BaseFragment<DemoFragmentFormBinding, FormViewModel>() {

    private var entity = FormEntity()

    @Override
    fun initParam() {
        //获取列表传入的实体
        val mBundle = getArguments()
        if (mBundle != null) {
            entity = mBundle!!.getParcelable("entity")
        }
    }

    @Override
    fun initContentView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle): Int {
        return R.layout.demo_fragment_form
    }

    @Override
    fun initVariableId(): Int {
        return BR.viewModel
    }

    @Override
    fun initData() {
        //通过binding拿到toolbar控件, 设置给Activity
        (getActivity() as AppCompatActivity).setSupportActionBar(binding.include.toolbar)
        //View层传参到ViewModel层
        viewModel.setFormEntity(entity)
        //初始化标题
        viewModel.initToolbar()
    }

    @Override
    fun initViewObservable() {
        //监听日期选择
        viewModel.uc.showDateDialogObservable.addOnPropertyChangedCallback(object :
            Observable.OnPropertyChangedCallback() {
            @Override
            fun onPropertyChanged(sender: Observable, propertyId: Int) {
                val calendar = Calendar.getInstance()
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DAY_OF_MONTH)
                val datePickerDialog = DatePickerDialog(getContext(), object : DatePickerDialog.OnDateSetListener() {
                    @Override
                    fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
                        viewModel.setBir(year, month, dayOfMonth)
                    }
                }, year, month, day)
                datePickerDialog.setMessage("生日选择")
                datePickerDialog.show()
            }
        })
        viewModel.entityJsonLiveData.observe(this, object : Observer<String>() {
            @Override
            fun onChanged(@Nullable submitJson: String) {
                MaterialDialogUtils.showBasicDialog(getContext(), "提交的json实体数据：\r\n$submitJson").show()
            }
        })
    }
}
