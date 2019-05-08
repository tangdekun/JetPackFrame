package com.djt.study.base.debug

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.djt.study.base.base.BaseActivity


class TestActivity : BaseActivity() {

    lateinit var model: TestViewModel

    companion object {
        private val TAG = TestActivity::class.java.name
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.djt.study.base.R.layout.activity_test)
        model = ViewModelProviders.of(this).get(TestViewModel::class.java)


    }


}
