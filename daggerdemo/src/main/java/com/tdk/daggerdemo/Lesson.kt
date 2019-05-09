package com.tdk.daggerdemo

import javax.inject.Inject

/**
 * @Author tangdekun
 * @Date 2018/8/1-13:41
 * @Email tangdekun0924@gmail.com
 */
class Lesson @Inject constructor() {

    lateinit var name: String

    var score: Int = 0

    constructor(name: String, score: Int) : this() {
        this.name = name
        this.score = score
    }

    override fun toString(): String {
        return "name:$name,score:$score"
    }

}