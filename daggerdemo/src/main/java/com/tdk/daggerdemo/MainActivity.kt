package com.tdk.daggerdemo

import android.annotation.SuppressLint
import android.os.Bundle
import com.djt.base.utils.KLog
import com.tdk.daggerdemo.base.BaseActivity
import com.tdk.daggerdemo.second.SecondActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity() {


    @Inject
    lateinit var student1: Student1


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        student1.name = "唐德坤"
        student1.age = 26
        student1.lesson?.name = "英语"
        student1.lesson?.score = 100
        message.text = student1.toString()
        KLog.d("MainActivity", student1.toString())
        enterButton.setOnClickListener {
            SecondActivity.toSecondActivity(this)
        }
    }
}
