package com.github.boybeak.v8canvas

import com.eclipsesource.v8.V8
import com.eclipsesource.v8.V8Object
import com.github.boybeak.canvas.IWebCanvas

interface IWebCanvasV8 : IWebCanvas {
    val v8: V8
    fun initialize(v8: V8)
    fun getContextV8(type: String): IWebCanvasContextV8
}