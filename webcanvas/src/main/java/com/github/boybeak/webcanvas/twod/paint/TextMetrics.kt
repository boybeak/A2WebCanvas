package com.github.boybeak.webcanvas.twod.paint

import android.graphics.Paint
import kotlin.math.abs

data class TextMetrics(
    val width: Float,
    val fontBoundingBoxAscent: Float,   // abs(paint.fontMetrics.top).toRpx
    val fontBoundingBoxDescent: Float,  // abs(paint.fontMetrics.bottom).toRpx
    val actualBoundingBoxAscent: Float, // abs(paint.fontMetrics.ascent).toRpx
    val actualBoundingBoxDescent: Float // abs(paint.fontMetrics.descent).toRpx
) {
    constructor(width: Float, paint: Paint) : this(
        width,
        abs(paint.fontMetrics.top),
        abs(paint.fontMetrics.bottom),
        abs(paint.fontMetrics.ascent),
        abs(paint.fontMetrics.descent)
    )
}