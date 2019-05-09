package com.tdk.daggerdemo

import org.junit.Assert.assertEquals
import org.junit.Test
import javax.inject.Inject

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Inject
    lateinit var mStudent1: Student1

    @Test
    fun addition_isCorrect() {

        println(mStudent1.toString())
        assertEquals(4, 2 + 2)
    }

}
