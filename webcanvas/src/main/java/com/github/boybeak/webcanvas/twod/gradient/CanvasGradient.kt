package com.github.boybeak.webcanvas.twod.gradient

import android.graphics.Shader
import java.lang.IllegalArgumentException
import java.util.UUID

abstract class CanvasGradient {

    companion object {
        val NONE = object : CanvasGradient() {
            override fun toShader(): Shader {
                throw IllegalArgumentException("DO NOT USE THIS, REPLACE THIS WITH OTHER CanvasGradient")
            }
        }
    }

    val id = UUID.randomUUID().toString()
    private val colorStops = ArrayList<ColorStop>()

    internal val colors: IntArray get() = IntArray(colorStops.size) {
        colorStops[it].color
    }
    internal val positions: FloatArray get() = FloatArray(colorStops.size) {
        colorStops[it].offset
    }

    /**
     * @param offset A number between 0 and 1, inclusive, representing the position of the color stop. 0 represents the start of the gradient and 1 represents the end.
     * @param color A CSS <color> value representing the color of the stop.
     */
    fun addColorStop(offset: Float, color: Int) {
        colorStops.add(ColorStop(offset, color))
    }
    abstract fun toShader(): Shader

    internal class ColorStop(val offset: Float, val color: Int)
}