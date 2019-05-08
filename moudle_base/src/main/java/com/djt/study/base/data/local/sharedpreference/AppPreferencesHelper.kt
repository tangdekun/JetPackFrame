package com.djt.study.base.data.local.sharedpreference

import android.content.Context
import com.djt.study.base.di.annotation.PreferenceInfo
import net.grandcentrix.tray.TrayPreferences
import net.grandcentrix.tray.core.TrayStorage
import javax.inject.Inject

/**
 * @Author tangdekun
 * @Date 2018/8/2-15:43
 * @Email tangdekun0924@gmail.com
 */
class AppPreferencesHelper @Inject constructor(context: Context, @PreferenceInfo fileName: String) : TrayPreferences(context, fileName, 1, TrayStorage.Type.DEVICE),
    PreferencesHelper {



    override fun onUpgrade(oldVersion: Int, newVersion: Int) {
        super.onUpgrade(oldVersion, newVersion)
    }



}