package com.github.boybeak.webcanvas.twod

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.os.Handler
import com.github.boybeak.webcanvas.IWebCanvas
import com.github.boybeak.webcanvas.IWebCanvasContext
import com.github.boybeak.webcanvas.IWebCanvasSurfaceHolder

class CanvasRenderingContext2D(iWebCanvas: IWebCanvas2D) : AbsCanvasRenderingContext2D(iWebCanvas) {

    private val paint = Paint()

    private var path: Path? = null

    private fun stroke(callback: (Paint) -> Unit) {
        paint.style = Paint.Style.STROKE
        callback.invoke(paint)
    }
    private fun fill(callback: (Paint) -> Unit) {
        paint.style = Paint.Style.FILL
        callback.invoke(paint)
    }

    override fun strokeRect(x: Float, y: Float, width: Float, height: Float) {
        stroke {
            canvas.drawRect(x, y, x + width, y + height, it)
            postInvalidate()
        }
    }

    override fun fillRect(x: Float, y: Float, width: Float, height: Float) {
        fill {
            canvas.drawRect(x, y, x + width, y + height, it)
            postInvalidate()
        }
    }

    override fun beginPath() {
        path = Path()
    }

    override fun moveTo(x: Float, y: Float) {
        path?.moveTo(x, y)
    }

    override fun lineTo(x: Float, y: Float) {
        path?.lineTo(x, y)
    }

    override fun closePath() {
        path?.close()
    }

    override fun stroke() {
        stroke {
            canvas.drawPath(path!!, it)
            postInvalidate()
        }
    }
}