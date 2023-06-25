package com.github.boybeak.webcanvas.render

import android.graphics.Canvas
import android.graphics.Color
import android.util.Log
import com.github.boybeak.webcanvas.twod.IWebCanvas2D

internal class Renderer2D(canvas2D: IWebCanvas2D) : AbsRenderer(canvas2D.surfaceHolder, canvas2D.getPlatformContext()) {

    companion object {
        private const val TAG = "SurfaceHolder2D"
    }

    private var currentCanvas: Canvas? = null
    val canvas: Canvas get() {
        if (currentCanvas == null) {
            nextCanvas()
        }
        return currentCanvas!!
    }

    override fun onRender() {
        if (currentCanvas != null) {
            currentCanvas?.restore()
            surfaceHolder.unlockCanvasAndPost(currentCanvas)
        }
        nextCanvas()
    }

    init {
        initDraw()
    }

    private fun initDraw() {
        handler.post {
            nextCanvas()
            resetCanvas()
        }
    }

    private fun resetCanvas() {
        val canvas = currentCanvas ?: return
        canvas.drawColor(Color.WHITE)
        canvas.save()
    }

    private fun nextCanvas() {
        Log.d(TAG, "nextCanvas")
        currentCanvas = surfaceHolder.lockCanvas()
        resetCanvas()

    }

}