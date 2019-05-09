package com.tdk.daggerdemo

/**
 * @Author tangdekun
 * @Date 2018/7/30-10:43
 * @Email tangdekun0924@gmail.com
 */
class Student  constructor(val name: String, val age: Int) {

    lateinit var lesson: Lesson
    override fun toString(): String {
        return "name:$name,age:$age,课程:${lesson.name}:${lesson.score}"
    }

    constructor(name: String, age: Int, lesson: Lesson) : this(name, age) {
        this.lesson = lesson
    }

}