package com.github.boybeak.v8webcanvas.twod.gradient

import com.eclipsesource.v8.V8Object
import com.eclipsesource.v8.V8Value
import com.github.boybeak.v8x.binding.Key
import com.github.boybeak.v8x.binding.V8Binding
import com.github.boybeak.v8x.binding.annotation.V8Field
import com.github.boybeak.v8x.binding.annotation.V8Method
import com.github.boybeak.webcanvas.twod.gradient.CanvasGradient
import com.github.boybeak.webcanvas.utils.HtmlColor

class V8CanvasGradient(private val gradient: CanvasGradient) : V8Binding {

    companion object {
        private const val KEY_V8_CANVAS_GRADIENT_ID = "_v8CanvasGradientId"
        fun isGradient(v8obj: V8Object): Boolean {
            return v8obj.getType(KEY_V8_CANVAS_GRADIENT_ID) == V8Value.STRING
        }
        fun getId(v8obj: V8Object): String {
            return v8obj.getString(KEY_V8_CANVAS_GRADIENT_ID)
        }
    }

    @V8Field
    private val _v8CanvasGradientId = gradient.id

    override fun getBindingId(): String {
        return gradient.id
    }

    override fun onBindingChanged(
        target: V8Object,
        fieldInfo: Key,
        newValue: Any?,
        oldValue: Any?
    ) {}

    @V8Method
    fun addColorStop(offset: Double, color: String) {
        gradient.addColorStop(offset.toFloat(), HtmlColor.parseColor(color))
    }
}