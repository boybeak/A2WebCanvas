package com.github.boybeak.v8canvas.onscreen

import android.content.Context
import android.util.AttributeSet
import com.eclipsesource.v8.V8
import com.eclipsesource.v8.V8Object
import com.github.boybeak.canvas.onscreen.WebCanvasOnscreen
import com.github.boybeak.v8canvas.context.V8WebCanvasContextOnscreen2D
import com.github.boybeak.v8x.binding.Key
import com.github.boybeak.v8x.binding.V8Binding
import com.github.boybeak.v8x.binding.annotation.V8Method
import java.lang.IllegalArgumentException

class V8WebCanvasOnscreen : WebCanvasOnscreen, V8Binding {

    private var v8Internal: V8? = null
    val v8: V8
        get() {
        if (v8Internal == null) {
            throw IllegalStateException("You must initialize v8 environment before use it")
        }
        return v8Internal!!
    }

    private var v8CanvasContext: V8Binding? = null

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun getBindingId(): String {
        return hashCode().toString()
    }

    fun initialize(v8: V8) {
        this.v8Internal = v8
    }

    @V8Method(jsFuncName = "getContext")
    fun getContextV8(type: String): V8Object {
        if (v8CanvasContext == null) {
            v8CanvasContext = when(type) {
                "2d" -> {
                    V8WebCanvasContextOnscreen2D(this)
                }
                "webgl" -> TODO("Not support yet")
                "webgl2" -> TODO("Not support yet")
                else -> throw IllegalArgumentException("The type - $type not supported")
            }
        }
        return v8CanvasContext!!.getMyBinding(v8)
    }


}