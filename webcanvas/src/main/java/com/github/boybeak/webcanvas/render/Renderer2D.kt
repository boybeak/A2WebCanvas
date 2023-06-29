package com.github.boybeak.webcanvas.render

import android.graphics.Canvas
import android.graphics.Color
import android.util.Log
import android.view.SurfaceHolder
import com.github.boybeak.webcanvas.twod.CanvasProvider
import com.github.boybeak.webcanvas.twod.IWebCanvas2D

internal class Renderer2D(canvas2D: IWebCanvas2D) : AbsRenderer2D(canvas2D.getPlatformContext()) {

    companion object {
        private const val TAG = "Renderer2D"
    }

    private val surfaceHolder = canvas2D.surfaceHolder

    private var currentCanvas: Canvas? = null
    override val canvas: Canvas get() {
        if (currentCanvas == null) {
            makeCanvas()
        }
        return currentCanvas!!
    }

    private var callback: CanvasProvider.Callback? = null

    override fun setCallback(callback: CanvasProvider.Callback?) {
        this.callback = callback
    }

    init {
        surfaceHolder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
            }

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
                handler.post {
                    makeCanvas()
                    commitCanvas()
                }
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                currentCanvas = null
            }
        })
    }

    init {
        handler.post {
            makeCanvas()
            commitCanvas()
        }
    }

    private fun makeCanvas() {
        currentCanvas = surfaceHolder.lockCanvas()
        callback?.onCanvasCreated(currentCanvas!!)
    }

    override fun onRender() {
        commitCanvas()
    }

    private fun commitCanvas() {
        Log.d(TAG, "invalidate currentCanvas=$currentCanvas")
        val c = currentCanvas ?: return
        callback?.onCanvasCommit(c)
        surfaceHolder.unlockCanvasAndPost(c)
        currentCanvas = null
        callback?.onCanvasDestroyed()
    }

}