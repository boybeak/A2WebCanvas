package com.github.boybeak.canvas.context

import android.graphics.Canvas
import com.github.boybeak.canvas._2d.IWebCanvasContext2D
import com.github.boybeak.canvas.onscreen.IWebCanvasContextOnscreen
import com.github.boybeak.canvas.onscreen.IWebCanvasOnscreen
import com.github.boybeak.canvas.render.RenderExecutor

class WebCanvasContextOnscreen2D(override val canvas: IWebCanvasOnscreen) : IWebCanvasContextOnscreen, IWebCanvasContext2D {
    override val renderExecutor: RenderExecutor = RenderExecutor(canvas.androidContext, object : RenderExecutor.Callback {
        override fun onRender() {

        }

        override fun onStop() {

        }
    })

    override val androidCanvas: Canvas
        get() = canvas.surfaceHolder.lockCanvas()

    override fun startRender() {
        TODO("Not yet implemented")
    }

    override fun stopRender() {
        TODO("Not yet implemented")
    }

}