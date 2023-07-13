package com.github.boybeak.webcanvas.twod

import android.graphics.Canvas
import com.github.boybeak.webcanvas.render.AbsRenderer2D

abstract class AbsCanvasRenderingContext2D : ICanvasRenderingContext2D {

    internal abstract val renderer: AbsRenderer2D
    internal val canvas: Canvas get() = renderer.canvas

    override fun onResume() {
        renderer.onResume()
    }

    override fun onPause() {
        renderer.onPause()
    }

    override fun onRenderModeChanged(mode: Int) {
        renderer.setRenderMode(mode)
    }

    override fun requestRender() {
        renderer.requestRender()
    }

    internal fun postInvalidate() {
        if (renderer.isAutoRendering) {
            renderer.requestRender()
        }
    }

}