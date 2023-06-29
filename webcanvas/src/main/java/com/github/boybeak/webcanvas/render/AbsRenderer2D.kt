package com.github.boybeak.webcanvas.render

import android.content.Context
import android.graphics.Canvas
import android.view.SurfaceHolder

internal abstract class AbsRenderer2D(context: Context) : AbsRenderer(context) {
    internal abstract val canvas: Canvas
}