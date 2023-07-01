package com.github.boybeak.a2webcanvas.app

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.util.AttributeSet
import android.view.View
import com.github.boybeak.webcanvas.twod.geometry.ArcTo
import com.github.boybeak.webcanvas.twod.geometry.VectorF2D
import kotlin.math.sin

class PainterView : View {

    companion object {
        private const val TAG = "PainterView"
    }

    private val paint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.YELLOW
        isAntiAlias = true
    }

    private val Number.dp: Float get() {
        return this.toFloat() * context.resources.displayMetrics.density
    }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        setLayerType(LAYER_TYPE_HARDWARE, null)
    }

    override fun onDraw(canvas: Canvas?) {
        val c = canvas ?: return
        c.drawColor(Color.WHITE)
        c.save()

        c.drawRect(0F, 0F, width / 2F, height / 2F, paint)

        clearRect(c, width / 4F, height / 4F, width / 2F, height / 2F)

        c.restore()
    }

    fun clearRect(canvas: Canvas, x: Float, y: Float, width: Float, height: Float) {
        canvas.save()
        canvas.clipRect(x, y, x + width, y + height)
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
        canvas.restore()
    }

    fun clearPath(canvas: Canvas, x: Float, y: Float, width: Float, height: Float) {
        /*canvas.save()
        val path = Path()
        path.addRect(x, y, x + width, y + height, Path.Direction.CW)
        canvas.drawPath(path, Paint().apply {
            xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
        })
        canvas.restore()*/
    }

    fun drawPaint(canvas: Canvas, x: Float, y: Float, width: Float, height: Float) {
        canvas.save()
        canvas.clipRect(x, y, x + width, y + height)
        canvas.drawPaint(Paint().apply {
            xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        })
        canvas.restore()
    }

}