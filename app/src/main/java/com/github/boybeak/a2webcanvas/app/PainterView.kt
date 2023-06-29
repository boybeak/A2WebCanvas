package com.github.boybeak.a2webcanvas.app

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF
import android.util.AttributeSet
import android.view.View
import com.github.boybeak.webcanvas.twod.geometry.ArcTo
import com.github.boybeak.webcanvas.twod.geometry.VectorF2D
import kotlin.math.sin

class PainterView : View {

    companion object {
        private const val TAG = "PainterView"
    }

    private val soePath = Path()
    private val soePaint = Paint()

    private val ocPath = Path().apply {
    }
    private val ocPaint = Paint()

    private val midLinePath = Path()

    private val x0 = 200.dp
    private val y0 = 20.dp
    private val x1 = 200.dp
    private val y1 = 130.dp
    private val x2 = 50.dp
    private val y2 = 20.dp
    private val r = 40.dp

    private val arcToF = ArcTo()

    private val s = PointF(x0, y0)
    private val o = PointF(x1, y1)
    private val e = PointF(x2, y2)

    private val os = VectorF2D(o, s)
    private val oe = VectorF2D(o, e)

    private val soe = os.radianWith(oe)
    private val soc = soe / 2
    private val ocLen = r / sin(soc)

    private val oc = os.angleMidWith(oe).apply {
        set(normalized.x * ocLen + o.x, normalized.y * ocLen + o.y)
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
        arcToF.set(x0, y0, x1, y1, x2, y2, r)
    }

    override fun onDraw(canvas: Canvas?) {
        val c = canvas ?: return

    }
}