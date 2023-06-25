package com.github.boybeak.webcanvas.twod

import android.graphics.Canvas
import android.os.Handler
import com.github.boybeak.webcanvas.render.Renderer2D

abstract class AbsCanvasRenderingContext2D(iWebCanvas: IWebCanvas2D) : ICanvasRenderingContext2D {

    private val renderer: Renderer2D = Renderer2D(iWebCanvas)
    internal val canvas: Canvas get() = renderer.canvas
    override val handler: Handler get() = renderer.handler

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