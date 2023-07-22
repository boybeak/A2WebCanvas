package com.github.boybeak.v8canvas.offscreen

import com.eclipsesource.v8.V8
import com.eclipsesource.v8.V8Object
import com.github.boybeak.canvas.offscreen.WebCanvasOffscreen
import com.github.boybeak.v8canvas.IWebCanvasContextV8
import com.github.boybeak.v8canvas.IWebCanvasV8
import com.github.boybeak.v8canvas.context.V8WebCanvasContextOffscreen2D
import com.github.boybeak.v8x.binding.V8Binding
import com.github.boybeak.v8x.binding.annotation.V8Method
import java.lang.IllegalArgumentException

class V8WebCanvasOffscreen : WebCanvasOffscreen(), V8Binding, IWebCanvasV8 {

    private var v8Internal: V8? = null
    override val v8: V8
        get() {
            if (v8Internal == null) {
                throw IllegalStateException("You must initialize v8 environment before use it")
            }
            return v8Internal!!
        }
    private var v8CanvasContext: IWebCanvasContextV8? = null

    override fun getBindingId(): String {
        return hashCode().toString()
    }

    override fun initialize(v8: V8) {
        this.v8Internal = v8
    }

    @V8Method(jsFuncName = "getContext")
    override fun getContextV8(type: String): IWebCanvasContextV8 {
        if (v8CanvasContext == null) {
            v8CanvasContext = when(type) {
                "2d" -> V8WebCanvasContextOffscreen2D(this)
                "webgl" -> TODO("Not support yet")
                "webgl2" -> TODO("Not support yet")
                else -> throw IllegalArgumentException("The type - $type not supported")
            }
        }
        return v8CanvasContext!!
    }
}