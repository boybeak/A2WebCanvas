package com.github.boybeak.canvas.context

import android.graphics.Canvas
import android.util.Log
import com.github.boybeak.canvas._2d.IWebCanvasContext2D
import com.github.boybeak.canvas.onscreen.AbsWebCanvasContextOnscreen
import com.github.boybeak.canvas.onscreen.IWebCanvasContextOnscreen
import com.github.boybeak.canvas.onscreen.IWebCanvasOnscreen
import com.github.boybeak.canvas.painter.IPainter2D
import com.github.boybeak.canvas.painter.Painter2D
import com.github.boybeak.canvas.render.RenderExecutor

class WebCanvasContextOnscreen2D constructor(canvas: IWebCanvasOnscreen) : AbsWebCanvasContextOnscreen(canvas), IWebCanvasContext2D {

    companion object {
        private const val TAG = "WebCanvasContextOnscreen2D"
    }

    override val canvasPainter: IPainter2D = Painter2D(this)

    private var canvasInner: Canvas? = null
        get() {
            if (field == null) {
                field = canvas.surfaceHolder.lockCanvas()
            }
            return field!!
        }

    override val androidCanvas: Canvas
        get() = canvasInner!!

    fun draw(onDraw: WebCanvasContextOnscreen2D.() -> Unit) {
        post {
            onDraw.invoke(this)
        }
    }

    override fun onFrameRender() {
        if (canvasInner != null) {
            canvas.surfaceHolder.unlockCanvasAndPost(canvasInner!!)
            canvasInner = null
        }
    }

    override fun onStopRender() {
        Log.d(TAG, "onStopRender")
    }

}