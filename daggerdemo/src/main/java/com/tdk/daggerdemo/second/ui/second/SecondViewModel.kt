package com.tdk.daggerdemo.second.ui.second

import android.content.Context
import android.content.SharedPreferences
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.tdk.daggerdemo.AppComponentHolder
import javax.inject.Inject

class SecondViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    val name = ObservableField<String>()

    @Inject
    lateinit var context: Context

    @Inject
    lateinit var context1: Context


    @Inject
    lateinit var mSharedPreferences: SharedPreferences

    init {
        DaggerSecondFragmentComponent.builder()
                .appComponent(AppComponentHolder.mAppComponent)
                .build()
                .inject(this)
    }

    fun getName(): String = "${mSharedPreferences.getString("name", "不知道")} \n  $context +\n  $context1 "


}
