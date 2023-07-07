package com.github.boybeak.webcanvas.twod.gradient

import android.graphics.Shader
import java.util.UUID

abstract class CanvasGradient {

    val id = UUID.randomUUID().toString()
    internal val colorStops = ArrayList<ColorStop>()

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