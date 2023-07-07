package com.github.boybeak.webcanvas.twod.gradient

import android.graphics.RadialGradient
import android.graphics.Shader
import android.os.Build

class RadialGradient internal constructor(private val x1: Float, private val y1: Float, private val r1: Float,
                                          private val x2: Float, private val y2: Float, private val r2: Float,) : CanvasGradient() {
    override fun toShader(): Shader {
        // TODO effect not match
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            RadialGradient(x1, y1, r1, x2, y2, r2,
                LongArray(colors.size) { colors[it].toLong() }, positions, Shader.TileMode.CLAMP)
        } else {
            RadialGradient(x1, y1, r1, colors, positions, Shader.TileMode.CLAMP)
        }
    }
}