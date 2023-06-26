package com.github.boybeak.webcanvas.render

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PixelFormat
import android.util.Log
import android.view.SurfaceHolder
import com.github.boybeak.webcanvas.twod.IWebCanvas2D

internal class Renderer2D(canvas2D: IWebCanvas2D) : AbsRenderer(canvas2D.surfaceHolder, canvas2D.getPlatformContext()) {

    companion object {
        private const val TAG = "Renderer2D"
    }

    private var currentCanvas: Canvas? = null
    val canvas: Canvas get() {
        Log.d(TAG, "get canvas currentCanvas=$currentCanvas")
        if (currentCanvas == null) {
            nextCanvas()
        }
        return currentCanvas!!
    }

    var resetSaveIndex = 0
        private set

    init {
        surfaceHolder.addCallback(object : SurfaceHolder.Callback2 {
            override fun surfaceCreated(holder: SurfaceHolder) {
                Log.d(TAG, "surfaceCreated")
            }

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
                Log.d(TAG, "surfaceChanged")
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                Log.d(TAG, "surfaceDestroyed")
            }

            override fun surfaceRedrawNeeded(holder: SurfaceHolder) {
                Log.d(TAG, "surfaceRedrawNeeded thread=${Thread.currentThread().name}")
            }
        })
        /*surfaceHolder.addCallback(object : SurfaceHolder.Callback  {
            override fun surfaceCreated(holder: SurfaceHolder) {
                Log.d(TAG, "surfaceCreated")
            }

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
                Log.d(TAG, "surfaceChanged")
                handler.post {
                    nextCanvas()
                    onRender()
                }
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                currentCanvas = null
                Log.d(TAG, "surfaceDestroyed")
            }
        })*/
    }

    override fun onRender() {
        postCanvas()
        nextCanvas()
    }

    init {
        initDraw()
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
        handler.post {
            nextCanvas()
        }
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
        handler.post {
            postCanvas()
        }
    }

    private fun initDraw() {
        handler.post {
            nextCanvas()
        }
    }

    private fun resetCanvas() {
        val canvas = currentCanvas ?: return
        canvas.drawColor(Color.WHITE)
        resetSaveIndex = canvas.save()
    }

    private fun nextCanvas() {
        currentCanvas = surfaceHolder.lockCanvas()
        Log.d(TAG, "nextCanvas currentCanvas=$currentCanvas")
        resetCanvas()
    }

    private fun postCanvas() {
        val canvas = currentCanvas ?: return
        if (canvas.saveCount > resetSaveIndex) {
            canvas.restore()
        }
        surfaceHolder.unlockCanvasAndPost(currentCanvas)
    }

}