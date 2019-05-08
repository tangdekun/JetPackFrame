package com.djt.study.base.debug

import com.djt.base.utils.KLog
import com.djt.study.base.mvvm.BaseViewModel

class TestViewModel : BaseViewModel() {
    companion object {
        private val TAG = TestViewModel::class.java.name
    }

    fun test() {
        KLog.a(TAG, "test")
    }
}