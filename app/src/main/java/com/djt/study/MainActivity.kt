package com.djt.study

import android.os.Bundle
import android.view.View
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import com.billy.cc.core.component.CC
import com.billy.cc.core.component.CCResult

/**
 * Created by guanrunbai on 2019/4/18.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun click(v: View) {
        val result = CC.obtainBuilder("ComponentA")
            .setActionName("showActivity")
            .build()
            .call()

    }

    fun demo(v: View) {
        val result = CC.obtainBuilder("ComponentDemo")
            .setActionName("showActivity")
            .build()
            .call()
    }

    fun flower(v: View){
        val result = CC.obtainBuilder("ComponentFlower")
            .setActionName("showActivity")
            .build()
            .call()
    }

}
