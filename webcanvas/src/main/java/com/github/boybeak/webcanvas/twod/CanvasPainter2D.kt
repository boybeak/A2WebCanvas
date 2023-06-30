package com.github.boybeak.webcanvas.twod

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import com.github.boybeak.webcanvas.twod.paint.TextMetrics
import com.github.boybeak.webcanvas.twod.paint.WebPaint
import com.github.boybeak.webcanvas.twod.geometry.AnchorPath
import com.github.boybeak.webcanvas.twod.image.IWebImage
import kotlin.math.PI

class CanvasPainter2D(private val canvasProvider: CanvasProvider) : ICanvasPainter2D, CanvasProvider.Callback {

    companion object {
        private const val TAG = "CanvasPainter"
    }

    private var path: AnchorPath? = null
    private val paint = WebPaint()

    private val drawImageSrcRect = Rect()
    private val drawImageDstRect = Rect()

    private val canvas: Canvas get() = canvasProvider.canvas

    private var resetSaveIndex = 0

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

    init {
        canvasProvider.setCallback(this)
    }

    override fun onCanvasCreated(canvas: Canvas) {
        canvas.drawColor(Color.WHITE)
        resetSaveIndex = canvas.save()
    }

    override fun onCanvasCommit(canvas: Canvas) {
        if (canvas.saveCount > resetSaveIndex) {
            canvas.restoreToCount(resetSaveIndex)
        }
    }

    override fun onCanvasDestroyed() {}

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
    }

    override fun fillText(text: String, x: Float, y: Float) {
        canvas.drawText(text, x, paint.computeRealY(y), paint.fillPaint)
    }

    override fun stroke() {
        canvas.drawPath(path!!, paint.strokePaint)
    }
    override fun strokeRect(x: Float, y: Float, width: Float, height: Float) {
        canvas.drawRect(x, y, x + width, y + height, paint.strokePaint)
    }

    override fun strokeText(text: String, x: Float, y: Float) {
        canvas.drawText(text, x, paint.computeRealY(y), paint.strokePaint)
    }

    override fun measureText(text: String): TextMetrics {
        return paint.measureText(text)
    }

    // Path related

    override fun beginPath() {
        path = AnchorPath()
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
            val deltaAngle = ((endAngle - startAngle) / PI * 180).toFloat()
            // Handle the special data
            // TODO("may cause other problems, need test")
            if (deltaAngle % 360 == 0F) 360F else deltaAngle - 360
        } else {
            ((endAngle - startAngle) / PI * 180).toFloat()
        }
        path?.addArc(x - radius, y - radius, x + radius, y + radius,
            (startAngle / PI * 180).toFloat(), sweepAngle)
    }

    override fun arcTo(x1: Float, y1: Float, x2: Float, y2: Float, radius: Float) {
        path?.arcTo(x1, y1, x2, y2, radius)
    }

    override fun bezierCurveTo(
        cp1x: Float,
        cp1y: Float,
        cp2x: Float,
        cp2y: Float,
        x: Float,
        y: Float
    ) {
        path?.cubicTo(cp1x, cp1y, cp2x, cp2y, x, y)
    }

    override fun lineTo(x: Float, y: Float) {
        path?.lineTo(x, y)
    }
    override fun moveTo(x: Float, y: Float) {
        path?.moveTo(x, y)
    }

    override fun quadraticCurveTo(cpx: Float, cpy: Float, x: Float, y: Float) {
        path?.quadTo(cpx, cpy, x, y)
    }

    override fun closePath() {
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

    // Image related

    override fun drawImage(image: IWebImage, dx: Int, dy: Int) {
        drawImage(image, dx, dy, image.width, image.height)
    }

    override fun drawImage(image: IWebImage, dx: Int, dy: Int, dWidth: Int, dHeight: Int) {
        drawImage(image, 0, 0, image.width, image.height, dx, dy, dWidth, dHeight)
    }

    override fun drawImage(
        image: IWebImage,
        sx: Int,
        sy: Int,
        sWidth: Int,
        sHeight: Int,
        dx: Int,
        dy: Int,
        dWidth: Int,
        dHeight: Int
    ) {
        val src = image.bitmap ?: return
        drawImageSrcRect.set(sx, sy, sx + sWidth, sy + sHeight)
        drawImageDstRect.set(dx, dy, dx + dWidth, dy + dHeight)
        canvas.drawBitmap(src, drawImageSrcRect, drawImageDstRect, null)
    }

}