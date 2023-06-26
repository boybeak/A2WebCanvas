package com.github.boybeak.webcanvas.twod

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import com.github.boybeak.webcanvas.twod.paint.TextMetrics
import com.github.boybeak.webcanvas.twod.paint.WebPaint

class CanvasRenderingContext2D(iWebCanvas: IWebCanvas2D) : AbsCanvasRenderingContext2D(iWebCanvas) {

    private val paint = WebPaint()

    private var path: Path? = null
    override var fillStyle: String
        get() = paint.fillStyle
        set(value) { paint.fillStyle = value }
    override var strokeStyle: String
        get() = paint.strokeStyle
        set(value) { paint.strokeStyle = value }
    override var filter: String?
        get() = paint.filter
        set(value) { paint.filter = value }
    override var font: String
        get() = paint.font
        set(value) { paint.font = value }
    override var lineCap: String
        get() = paint.lineCap
        set(value) { paint.lineCap = value }
    override var lineJoin: String
        get() = paint.lineJoin
        set(value) { paint.lineJoin = value }
    override var lineWidth: Float
        get() = paint.lineWidth
        set(value) { paint.lineWidth = value }
    override var shadowBlur: Float
        get() = paint.shadowBlur
        set(value) { paint.shadowBlur = value }
    override var shadowColor: String?
        get() = paint.shadowColor
        set(value) { paint.shadowColor = value }
    override var shadowOffsetX: Float
        get() = paint.shadowOffsetX
        set(value) { paint.shadowOffsetX = value }
    override var shadowOffsetY: Float
        get() = paint.shadowOffsetY
        set(value) { paint.shadowOffsetY = value }
    override var textAlign: String
        get() = paint.textAlign
        set(value) { paint.textAlign = value }
    override var textBaseline: String
        get() = paint.textBaseline
        set(value) { paint.textBaseline = value }

    override fun save() {
        canvas.save()
        paint.save()
    }

    override fun restore() {
        canvas.restore()
        paint.restore()
    }

    override fun reset() {
        canvas.restoreToCount(renderer.resetSaveIndex)
        paint.reset()
    }

    override fun clearRect(x: Float, y: Float, width: Float, height: Float) {
        val p = Paint()
        p.color = Color.TRANSPARENT
        p.isDither = true
        p.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        canvas.drawRect(x, y, x + width, y + height, p)
    }

    override fun fill() {
        canvas.drawPath(path!!, paint.fillPaint)
    }

    override fun fillRect(x: Float, y: Float, width: Float, height: Float) {
        canvas.drawRect(x, y, x + width, y + height, paint.fillPaint)
        postInvalidate()
    }

    override fun fillText(text: String, x: Float, y: Float) {
        canvas.drawText(text, x, paint.computeRealY(y), paint.fillPaint)
        postInvalidate()
    }

    override fun stroke() {
        canvas.drawPath(path!!, paint.strokePaint)
        postInvalidate()
    }
    override fun strokeRect(x: Float, y: Float, width: Float, height: Float) {
        canvas.drawRect(x, y, x + width, y + height, paint.strokePaint)
        postInvalidate()
    }

    override fun strokeText(text: String, x: Float, y: Float) {
        canvas.drawText(text, x, paint.computeRealY(y), paint.strokePaint)
        postInvalidate()
    }

    override fun measureText(text: String): TextMetrics {
        return paint.measureText(text)
    }

    override fun beginPath() {
        path = Path()
    }

    override fun lineTo(x: Float, y: Float) {
        path?.lineTo(x, y)
    }
    override fun moveTo(x: Float, y: Float) {
        path?.moveTo(x, y)
    }

    override fun closePath() {
        path?.close()
    }

}