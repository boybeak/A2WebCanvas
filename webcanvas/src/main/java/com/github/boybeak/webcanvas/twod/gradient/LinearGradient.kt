package com.github.boybeak.webcanvas.twod.gradient

import android.graphics.LinearGradient
import android.graphics.Shader

class LinearGradient internal constructor(private val x0: Float, private val y0: Float,
                                          private val x1: Float, private val y1: Float) : CanvasGradient() {

    override fun toShader(): Shader {
        return LinearGradient(x0, y0, x1, y1, colors, positions, Shader.TileMode.CLAMP)
    }
}