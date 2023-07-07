package com.github.boybeak.webcanvas.twod.paint

import android.graphics.Paint
import com.github.boybeak.webcanvas.twod.gradient.CanvasGradient
import com.github.boybeak.webcanvas.utils.HtmlColor

interface Style {

    fun setTo(paint: Paint)

    class ColorStyle(val color: Int) : Style {
        constructor(colorStr: String): this(HtmlColor.parseColor(colorStr))

        override fun setTo(paint: Paint) {
            paint.shader = null
            paint.color = color
        }
    }
    class GradientStyle(val gradient: CanvasGradient) : Style {
        override fun setTo(paint: Paint) {
            paint.shader = gradient.toShader()
        }
    }
}