package com.github.boybeak.a2webcanvas.app

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    companion object {
        private const val TAG = "ExampleUnitTest"
    }

    var name: String = ""
        set(value) {
            field = value
            println("set value=$value")
        }

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
    @Test
    fun testSet() {
        name = "123"
        name = "123"
        assert(name == "123")
    }
}