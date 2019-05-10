package com.tdk.daggerdemo.second.ui.second

import android.content.SharedPreferences
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class SecondViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    val name = ObservableField<String>()


    @Inject
    lateinit var mSharedPreferences: SharedPreferences


    fun getName(): String = "${mSharedPreferences.getString("name", "不知道")} \n  "


}
