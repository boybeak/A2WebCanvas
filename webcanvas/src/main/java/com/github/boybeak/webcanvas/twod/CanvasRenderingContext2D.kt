package com.github.boybeak.webcanvas.twod

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.util.Log
import com.github.boybeak.webcanvas.render.AbsRenderer2D
import com.github.boybeak.webcanvas.render.Renderer2D
import com.github.boybeak.webcanvas.twod.paint.TextMetrics
import com.github.boybeak.webcanvas.twod.paint.WebPaint
import kotlin.math.PI

class CanvasRenderingContext2D(iWebCanvas: IWebCanvas2D) : AbsCanvasRenderingContext2D(), Renderer2D.Callback {

    companion object {
        private const val TAG = "CanvasRenderingContext2D"
    }

    private val paint = WebPaint()

    private var path: Path? = null
    private var resetSaveIndex = 0
    override val renderer: AbsRenderer2D = Renderer2D(iWebCanvas, this)
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

    override fun onCanvasCreated(canvas: Canvas) {
        canvas.drawColor(Color.WHITE)
        resetSaveIndex = canvas.save()
    }

    override fun onCanvasCommit(canvas: Canvas) {
        if (canvas.saveCount > resetSaveIndex) {
            canvas.restoreToCount(resetSaveIndex)
        }
    }

    override fun save() {
        canvas.save()
        paint.save()
    }

    override fun restore() {
        canvas.restore()
        paint.restore()
    }

    override fun reset() {
        canvas.restoreToCount(resetSaveIndex)
        paint.reset()
        postInvalidate()
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
        Log.d(TAG, "fillRect canvas=$canvas")
        canvas.drawRect(x, y, x + width, y + height, paint.fillPaint)
        postInvalidate()
    }

    override fun fillText(text: String, x: Float, y: Float) {
        canvas.drawText(text, x, paint.computeRealY(y), paint.fillPaint)
        postInvalidate()
    }

    override fun stroke() {
        Log.d(TAG, "stroke canvas=$canvas")
        canvas.drawPath(path!!, paint.strokePaint)
        postInvalidate()
    }
    override fun strokeRect(x: Float, y: Float, width: Float, height: Float) {
        Log.d(TAG, "strokeRect canvas=$canvas")
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
        Log.d(TAG, "beginPath")
        path = Path()
    }

    override fun arc(
        x: Float,
        y: Float,
        radius: Float,
        startAngle: Float,
        endAngle: Float,
        counterclockwise: Boolean
    ) {
        val sweepAngle = if (counterclockwise) {
            ((endAngle - startAngle) / PI * 180).toFloat() - 360
        } else {
            ((endAngle - startAngle) / PI * 180).toFloat()
        }
        path?.addArc(x - radius, y - radius, x + radius, y + radius,
            (startAngle / PI * 180).toFloat(), sweepAngle)
    }

    override fun lineTo(x: Float, y: Float) {
        Log.d(TAG, "lineTo")
        path?.lineTo(x, y)
    }
    override fun moveTo(x: Float, y: Float) {
        Log.d(TAG, "moveTo")
        path?.moveTo(x, y)
    }

    override fun closePath() {
        Log.d(TAG, "closePath")
        path?.close()
    }

    override fun rotate(angle: Float) {
        canvas.rotate((angle / PI * 180).toFloat())
    }

    override fun scale(x: Float, y: Float) {
        canvas.scale(x, y)
    }

    override fun translate(x: Float, y: Float) {
        canvas.translate(x, y)
    }

}