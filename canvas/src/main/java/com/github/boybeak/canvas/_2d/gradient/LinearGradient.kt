package com.github.boybeak.canvas._2d.gradient

import android.graphics.LinearGradient
import android.graphics.Shader
import com.github.boybeak.canvas._2d.gradient.CanvasGradient

class LinearGradient internal constructor(private val x0: Float, private val y0: Float,
                                          private val x1: Float, private val y1: Float) : CanvasGradient() {

    override fun toShader(): Shader {
        return LinearGradient(x0, y0, x1, y1, colors, positions, Shader.TileMode.CLAMP)
    }
}