package com.github.boybeak.webcanvas.render

import android.content.Context
import android.graphics.Canvas
import android.view.SurfaceHolder

internal abstract class AbsRenderer2D(surfaceHolder: SurfaceHolder, context: Context) : AbsRenderer(surfaceHolder, context) {
    internal abstract val canvas: Canvas
}