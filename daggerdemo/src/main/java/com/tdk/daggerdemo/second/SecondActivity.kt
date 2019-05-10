package com.tdk.daggerdemo.second

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.tdk.daggerdemo.R
import com.tdk.daggerdemo.base.BaseActivity
import com.tdk.daggerdemo.second.ui.second.SecondFragment

class SecondActivity : BaseActivity() {

    companion object {

        fun toSecondActivity(context: Context) {
            var intent = Intent(context, SecondActivity::class.java)
            context.startActivity(intent)

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.daggerdemo_second_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, SecondFragment.newInstance())
                    .commitNow()
        }
    }


}
