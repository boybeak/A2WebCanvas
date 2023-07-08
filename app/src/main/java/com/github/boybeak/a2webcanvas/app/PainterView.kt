package com.github.boybeak.a2webcanvas.app

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.BlendMode
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Xfermode
import android.util.AttributeSet
import android.view.View
import com.github.boybeak.webcanvas.twod.geometry.ArcTo
import com.github.boybeak.webcanvas.twod.geometry.VectorF2D
import kotlin.math.sin

class PainterView : View {

    companion object {
        private const val TAG = "PainterView"
    }

    private val Number.dp: Float get() {
        return this.toFloat() * context.resources.displayMetrics.density
    }

    private val colorMatrix = ColorMatrix(
        floatArrayOf(
            1F, 0F, 0F, 0F, 0F,
            0F, 1F, 0F, 0F, 0F,
            0F, 0F, 1F, 0F, 0F,
            0F, 0F, 0F, 0.5F, 0F
        )
    )
    private val bitmap = BitmapFactory.decodeResource(resources, R.drawable.flower)
    private val paint = Paint().apply {
        this.xfermode = PorterDuffXfermode(PorterDuff.Mode.XOR)
        style = Paint.Style.FILL
//        colorFilter = ColorMatrixColorFilter(colorMatrix)
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
        canvas?.drawRect(0.dp, 0.dp, 160.dp, 160.dp, paint)
        paint.color = Color.RED
        canvas?.drawRect(100.dp, 100.dp, 260.dp, 260.dp, paint)
//        canvas?.drawBitmap(bitmap, 0.dp, 0.dp, paint)
    }

}