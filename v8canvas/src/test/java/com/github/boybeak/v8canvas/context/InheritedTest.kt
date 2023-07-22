package com.github.boybeak.v8canvas.context

import com.github.boybeak.v8x.binding.annotation.V8Method
import org.junit.Test

class InheritedTest {
    @Test
    fun testInherited() {
        val clz = V8WebCanvasContextOnscreen2D::class.java
        clz.methods.forEach {
            val v8Method = it.getAnnotation(V8Method::class.java)
            println("method.name=${it.name} v8Method=$v8Method")
        }
    }
}