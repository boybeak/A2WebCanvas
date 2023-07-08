package com.github.boybeak.webcanvas.twod.paint

import android.graphics.BlurMaskFilter
import android.graphics.Color
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.MaskFilter
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Xfermode
import android.util.Log
import com.github.boybeak.webcanvas.utils.HtmlColor
import java.lang.IllegalArgumentException
import java.util.Stack

class WebPaint {

    companion object {
        private const val TAG = "WebPaint"
    }

    private val paint = Paint().apply {
        isAntiAlias = true
    }
    val fillPaint get() = paint.apply {
        style = Paint.Style.FILL
        fillStyle.setTo(this)
        statePaint()
    }
    val strokePaint get() = paint.apply {
        style = Paint.Style.STROKE
        strokeStyle.setTo(paint)
        statePaint()
    }

    internal val currentPaint: Paint get() = paint

    private val currentState = State()
    private val stateStack = Stack<State>()

    /**
     * [ a, b, c, d, e,
     *   f, g, h, i, j,
     *   k, l, m, n, o,
     *   p, q, r, s, t ]
     *
     *   R’ = a*R + b*G + c*B + d*A + e;
     *   G’ = f*R + g*G + h*B + i*A + j;
     *   B’ = k*R + l*G + m*B + n*A + o;
     *   A’ = p*R + q*G + r*B + s*A + t;
     */
    private val colorMatrix = floatArrayOf(
        1F, 0F, 0F, 0F, 0F,
        0F, 1F, 0F, 0F, 0F,
        0F, 0F, 1F, 0F, 0F,
        0F, 0F, 0F, 1F, 0F
    )

    /**
     * Only support limited
     */
    private val xfermodeMap = mapOf<String, Xfermode>(
        "source-over" to PorterDuffXfermode(PorterDuff.Mode.SRC_OVER),
        "source-in" to PorterDuffXfermode(PorterDuff.Mode.SRC_IN),
        "source-out" to PorterDuffXfermode(PorterDuff.Mode.SRC_OUT),
        "source-atop" to PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP),

        "destination-over" to PorterDuffXfermode(PorterDuff.Mode.DST_OVER),
        "destination-in" to PorterDuffXfermode(PorterDuff.Mode.DST_IN),
        "destination-out" to PorterDuffXfermode(PorterDuff.Mode.DST_OUT),
        "destination-atop" to PorterDuffXfermode(PorterDuff.Mode.DST_ATOP),

        "lighter" to PorterDuffXfermode(PorterDuff.Mode.LIGHTEN),
        "xor" to PorterDuffXfermode(PorterDuff.Mode.XOR),
        "multiply" to PorterDuffXfermode(PorterDuff.Mode.MULTIPLY),

        "screen" to PorterDuffXfermode(PorterDuff.Mode.SCREEN),
        "overlay" to PorterDuffXfermode(PorterDuff.Mode.OVERLAY),
        "darken" to PorterDuffXfermode(PorterDuff.Mode.DARKEN),
        "lighten" to PorterDuffXfermode(PorterDuff.Mode.LIGHTEN),
    )

    var fillStyle: Style
        get() = currentState.fillStyle
        set(value) {
            currentState.fillStyle = value
        }
    var strokeStyle: Style
        get() = currentState.strokeStyle
        set(value) {
            currentState.strokeStyle = value
        }
    var filter: String?
        get() = currentState.filter
        set(value) {
            currentState.filter = value
            stateMaskFilter()
        }

    var font: String
        get() = currentState.font
        set(value) {
            currentState.font = value
            stateFont()
        }

    var globalAlpha: Float
        get() = currentState.globalAlpha
        set(value) {
            currentState.globalAlpha = value
            stateGlobalAlpha()
        }

    var globalCompositeOperation: String
        get() = currentState.globalCompositeOperation
        set(value) {
            currentState.globalCompositeOperation = value
            stateGlobalCompositeOperation()
        }

    var lineCap: String
        get() = currentState.lineCap
        set(value) {
            currentState.lineCap = value
            stateLineCap()
        }
    var lineJoin: String
        get() = currentState.lineJoin
        set(value) {
            currentState.lineJoin = value
            stateLineJoin()
        }
    var lineWidth: Float
        get() = currentState.lineWidth
        set(value) {
            currentState.lineWidth = value
            stateLineWidth()
        }

    var shadowBlur: Float
        get() = currentState.shadowBlur
        set(value) {
            currentState.shadowBlur = value
            stateShadow()
        }
    var shadowColor: String?
        get() = currentState.shadowColor
        set(value) {
            currentState.shadowColor = value
            stateShadow()
        }
    var shadowOffsetX: Float
        get() = currentState.shadowOffsetX
        set(value) {
            currentState.shadowOffsetX = value
            stateShadow()
        }
    var shadowOffsetY: Float
        get() = currentState.shadowOffsetY
        set(value) {
            currentState.shadowOffsetY = value
            stateShadow()
        }

    var textAlign: String
        get() = currentState.textAlign
        set(value) {
            currentState.textAlign = value
            stateTextAlign()
        }

    var textBaseline: String
        get() = currentState.textBaseline
        set(value) {
            currentState.textBaseline = value

        }

    fun save() {
        stateStack.push(currentState.copy())
    }
    fun restore() {
        val s = stateStack.pop()
        currentState.load(s)
    }

    fun reset() {
        stateStack.clear()
        paint.reset()
        currentState.load(State())
        statePaint()
    }

    fun computeRealY(y: Float): Float {
        return y - when(textBaseline) {
            "top" -> paint.fontMetrics.ascent
            "hanging" -> paint.fontMetrics.top
            "ideographic" -> paint.fontMetrics.bottom
            "bottom" -> paint.fontMetrics.descent
            "middle" -> (paint.fontMetrics.ascent + paint.fontMetrics.descent) / 2
            else -> 0F
        }
    }

    fun measureText(text: String): TextMetrics {
        return TextMetrics(paint.measureText(text), paint)
    }

    private fun statePaint() {
        stateMaskFilter()
        stateFont()
    }
    private fun stateMaskFilter() {
        paint.maskFilter = currentState.getMaskFilter()
    }
    private fun stateFont() {
        val font = currentState.getFont()
        paint.textSize = font.textSize
        paint.typeface = font.typeface
    }
    private fun stateGlobalAlpha() {
        colorMatrix[18] = globalAlpha
        paint.colorFilter = ColorMatrixColorFilter(colorMatrix)
    }
    private fun stateGlobalCompositeOperation() {
        val xfermode = xfermodeMap[globalCompositeOperation]
        if (xfermode == null) {
            Log.w(TAG, "globalCompositeOperation for $globalCompositeOperation not supported")
        }
        Log.d(TAG, "stateGlobalCompositeOperation globalCompositeOperation=$globalCompositeOperation")
        paint.xfermode = xfermode
    }
    private fun stateLineCap() {
        Log.d(TAG, "stateLineCap lineCap=$lineCap")
        paint.strokeCap = when(lineCap) {
            "butt" -> Paint.Cap.BUTT
            "round" -> Paint.Cap.ROUND
            "square" -> Paint.Cap.SQUARE
            else -> throw IllegalArgumentException("Unknown lineCap $lineCap")
        }
    }
    private fun stateLineJoin() {
        paint.strokeJoin = when(lineJoin) {
            "round" -> Paint.Join.ROUND
            "bevel" -> Paint.Join.BEVEL
            "miter" -> Paint.Join.MITER
            else -> throw IllegalArgumentException("Unknown lineJoin $lineJoin")
        }
    }
    private fun stateLineWidth() {
        paint.strokeWidth = currentState.lineWidth
    }

    private fun stateShadow() {
        val colorStr = shadowColor ?: return
        paint.setShadowLayer(shadowBlur, shadowOffsetX, shadowOffsetY, HtmlColor.parseColor(colorStr))
    }

    private fun stateTextAlign() {
        paint.textAlign = when(textAlign) {
            "left", "start" ->  {
                Paint.Align.LEFT
            }
            "right", "end" ->  {
                Paint.Align.RIGHT
            }
            "center" -> {
                Paint.Align.CENTER
            }
            else -> throw IllegalArgumentException("Unknown textAlign $textAlign")
        }
    }

    private class State(var fillStyle: Style = Style.ColorStyle(Color.BLACK),
                        var strokeStyle: Style = Style.ColorStyle(Color.BLACK),
                        var filter: String? = null,
                        var font: String = "10px sans-serif",
                        var globalAlpha: Float = 1F,
                        var globalCompositeOperation: String = "source-over",
                        var lineCap: String = "butt",
                        var lineJoin: String = "miter",
                        var lineWidth: Float = 1F,
                        var shadowBlur: Float = 0F,
                        var shadowColor: String? = null,
                        var shadowOffsetX: Float = 0F,
                        var shadowOffsetY: Float = 0F,
                        var textAlign: String = "start",
                        var textBaseline: String = "alphabetic") {

        companion object {
            private val NUM_REGEX = Regex("\\d+\\.?\\d*")
        }

        fun load(state: State) {
            this.fillStyle = state.fillStyle
            this.strokeStyle = state.strokeStyle
            this.filter = state.filter
            this.font = state.font
            this.globalAlpha = state.globalAlpha
            this.globalCompositeOperation = state.globalCompositeOperation
            this.lineCap = state.lineCap
            this.lineJoin = state.lineJoin
            this.lineWidth = state.lineWidth
            this.shadowBlur = state.shadowBlur
            this.shadowColor = state.shadowColor
            this.shadowOffsetX = state.shadowOffsetX
            this.shadowOffsetY = state.shadowOffsetY
            this.textAlign = state.textAlign
            this.textBaseline = state.textBaseline
        }
        fun copy(): State {
            return State(fillStyle, strokeStyle, filter, font, globalAlpha, globalCompositeOperation,
                lineCap, lineJoin, lineWidth,
                shadowBlur, shadowColor, shadowOffsetX, shadowOffsetY, textAlign, textBaseline)
        }

        fun getMaskFilter(): MaskFilter? {
            val f = filter ?: return null
            return when(f.substring(0, f.indexOf('('))) {
                "blur" -> {
                    val pixels = NUM_REGEX.find(f)?.value?.toFloatOrNull() ?: 0F
                    BlurMaskFilter(pixels, BlurMaskFilter.Blur.SOLID)
                }
                else -> throw IllegalArgumentException("Not supported type of mask filter $f")
            }
        }
        fun getFont(): Font {
            return Font.from(font)
        }
    }
}