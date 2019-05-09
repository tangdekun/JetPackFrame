package com.tdk.daggerdemo

import javax.inject.Inject

/**
 * @Author tangdekun
 * @Date 2018/8/1-15:59
 * @Email tangdekun0924@gmail.com
 */

class Student1 @Inject constructor() {


    var name: String? = null


    var age: Int = 0


    var lesson: Lesson? = null

    constructor(lesson: Lesson) : this() {
        this.lesson = lesson
    }

    override fun toString(): String {
        return "name:$name,age:$age,lesson:${lesson?.name},score:${lesson?.score}"
    }


}