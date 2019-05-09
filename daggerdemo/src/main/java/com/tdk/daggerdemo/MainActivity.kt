package com.tdk.daggerdemo

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.djt.base.utils.KLog
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {


    @Inject
    lateinit var student1: Student1


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DaggerMainComponent.builder()
            .build()
            .inject(this)
        student1.name = "唐德坤"
        student1.age = 26
        student1.lesson?.name = "英语"
        student1.lesson?.score = 100
        daggerdemo_tv.text = student1.toString()
        KLog.d("MainActivity", student1.toString())
    }
}
