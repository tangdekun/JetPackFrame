package com.djt.study.base.base

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.android.AndroidInjection

/**
 * @Author tangdekun
 * @Date 2018/8/10-9:27
 * @Email tangdekun0924@gmail.com
 */
open class BaseBroadcastReceiver : BroadcastReceiver() {


    override fun onReceive(mContext: Context?, mIntent: Intent?) {
        AndroidInjection.inject(this, mContext)
    }
}