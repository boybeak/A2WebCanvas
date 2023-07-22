package com.github.boybeak.v8canvas.context

import android.util.Log
import com.eclipsesource.v8.V8
import com.eclipsesource.v8.V8Object
import com.github.boybeak.v8canvas._2d.BaseWebCanvasContext2DV8
import com.github.boybeak.v8canvas.onscreen.V8WebCanvasOnscreen
import com.github.boybeak.v8x.binding.annotation.V8Method

class V8WebCanvasContextOnscreen2D(canvas: V8WebCanvasOnscreen) : BaseWebCanvasContext2DV8(canvas) {

    companion object {
        private const val TAG = "V8WebCanvasContextOnscreen2D"
    }

    fun abc() {}

    override fun getMyBinding(v8: V8): V8Object {
        return super.getMyBinding(v8).also {
            it.registerJavaMethod(this, "abc", "abc", emptyArray())
            this::class.java.methods.forEach { method ->
                Log.d(TAG, "getMyBinding it.name=${method.name} annotation=${method.getAnnotation(V8Method::class.java)}")
            }
            Log.d(TAG, "getMyBinding keys=[${it.keys.joinToString()}]")
        }
    }
}