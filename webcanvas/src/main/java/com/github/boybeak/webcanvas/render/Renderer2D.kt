package com.github.boybeak.webcanvas.render

import android.graphics.Canvas
import android.graphics.Color
import android.util.Log
import com.github.boybeak.webcanvas.twod.IWebCanvas2D

internal class Renderer2D(canvas2D: IWebCanvas2D, private val callback: Callback) : AbsRenderer2D(canvas2D.surfaceHolder, canvas2D.getPlatformContext()) {

    companion object {
        private const val TAG = "Renderer2D"
    }

    private var currentCanvas: Canvas? = null
    override val canvas: Canvas get() {
        if (currentCanvas == null) {
            makeCanvas()
        }
        return currentCanvas!!
    }

    private fun makeCanvas() {
        currentCanvas = surfaceHolder.lockCanvas()
        currentCanvas?.drawColor(Color.WHITE)
        callback.onCanvasCreated(currentCanvas!!)
    }

    override fun onRender() {
        commitCanvas()
    }

    private fun commitCanvas() {
        Log.d(TAG, "invalidate currentCanvas=$currentCanvas")
        val c = currentCanvas ?: return
        callback.onCanvasCommit(c)
        surfaceHolder.unlockCanvasAndPost(c)
        currentCanvas = null
    }

    interface Callback {
        fun onCanvasCreated(canvas: Canvas)
        fun onCanvasCommit(canvas: Canvas)

    }

}