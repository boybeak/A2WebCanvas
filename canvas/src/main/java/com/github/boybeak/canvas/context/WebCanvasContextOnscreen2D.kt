package com.github.boybeak.canvas.context

import android.graphics.Canvas
import android.util.Log
import com.github.boybeak.canvas._2d.IWebCanvasContext2D
import com.github.boybeak.canvas.onscreen.AbsWebCanvasContextOnscreen
import com.github.boybeak.canvas.onscreen.IWebCanvasContextOnscreen
import com.github.boybeak.canvas.onscreen.IWebCanvasOnscreen
import com.github.boybeak.canvas.render.RenderExecutor

class WebCanvasContextOnscreen2D(canvas: IWebCanvasOnscreen) : AbsWebCanvasContextOnscreen(canvas), IWebCanvasContext2D {

    companion object {
        private const val TAG = "WebCanvasContextOnscreen2D"
    }

    override val androidCanvas: Canvas
        get() = canvas.surfaceHolder.lockCanvas()

    override fun onFrameRender() {
        Log.d(TAG, "onRenderFrame")
    }

    override fun onStopRender() {
        Log.d(TAG, "onStopRender")
    }

}