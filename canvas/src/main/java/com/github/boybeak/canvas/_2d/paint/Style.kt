package com.github.boybeak.canvas._2d.paint

import android.graphics.Paint
import com.github.boybeak.canvas.utils.HtmlColor
import com.github.boybeak.canvas._2d.gradient.CanvasGradient

interface Style {

    fun setTo(paint: Paint)

    class ColorStyle(var color: Int) : Style {
        constructor(colorStr: String): this(HtmlColor.parseColor(colorStr))

        override fun setTo(paint: Paint) {
            paint.shader = null
            paint.color = color
        }

        fun setColorStr(colorStr: String) {
            color = HtmlColor.parseColor(colorStr)
        }
    }
    class GradientStyle(var gradient: CanvasGradient) : Style {
        override fun setTo(paint: Paint) {
            paint.shader = gradient.toShader()
        }
    }
}