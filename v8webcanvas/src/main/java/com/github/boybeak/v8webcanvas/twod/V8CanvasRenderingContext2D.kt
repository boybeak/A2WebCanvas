package com.github.boybeak.v8webcanvas.twod

import com.eclipsesource.v8.V8Object
import com.github.boybeak.v8x.binding.Key
import com.github.boybeak.v8x.binding.V8Binding
import com.github.boybeak.v8x.binding.annotation.V8Method
import com.github.boybeak.webcanvas.twod.CanvasRenderingContext2D

class V8CanvasRenderingContext2D(private val context2D: CanvasRenderingContext2D) : V8Binding {
    override fun getBindingId(): String {
        return hashCode().toString()
    }

    override fun onBindingChanged(
        target: V8Object,
        fieldInfo: Key,
        newValue: Any?,
        oldValue: Any?
    ) {
    }

    @V8Method
    fun fillRect(x: Double, y: Double, w: Double, h: Double) {
        context2D.fillRect(x.toFloat(), y.toFloat(), w.toFloat(), h.toFloat())
    }

}