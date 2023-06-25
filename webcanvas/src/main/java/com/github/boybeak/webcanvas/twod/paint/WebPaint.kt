package com.github.boybeak.webcanvas.twod.paint

import android.graphics.BlurMaskFilter
import android.graphics.MaskFilter
import android.graphics.Paint
import com.github.boybeak.webcanvas.utils.HtmlColor
import java.lang.IllegalArgumentException
import java.util.Stack

class WebPaint {

    private val paint = Paint()
    private val currentState = State()
    private val stateStack = Stack<State>()

    var fillStyle: String
        get() = currentState.fillStyle
        set(value) {
            currentState.fillStyle = value
        }
    var strokeStyle: String
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

    val fillPaint get() = paint.apply {
        style = Paint.Style.FILL
        color = HtmlColor.parseColor(currentState.fillStyle)
        statePaint()
    }
    val strokePaint get() = paint.apply {
        style = Paint.Style.STROKE
        color = HtmlColor.parseColor(currentState.strokeStyle)
        statePaint()
    }

    fun save() {
        stateStack.push(currentState.copy())
    }
    fun restore() {
        val s = stateStack.pop()
        currentState.load(s)
    }

    private fun statePaint() {
        stateMaskFilter()
    }
    private fun stateMaskFilter() {
        paint.maskFilter = currentState.getMaskFilter()
    }
    private fun stateFont() {
        val font = currentState.getFont()
        paint.textSize = font.textSize
        paint.typeface = font.typeface
    }
    private fun stateLineCap() {
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


    private class State(var fillStyle: String = "#000",
                        var strokeStyle: String = "#000",
                        var filter: String? = null,
                        var font: String = "10px sans-serif",
                        var lineCap: String = "butt",
                        var lineJoin: String = "miter",
                        var lineWidth: Float = 1F) {

        companion object {
            private val NUM_REGEX = Regex("\\d+\\.?\\d*")
        }

        fun load(state: State) {
            this.fillStyle = state.fillStyle
            this.strokeStyle = state.strokeStyle
            this.filter = state.filter
            this.font = state.font
            this.lineCap = state.lineCap
            this.lineJoin = state.lineJoin
            this.lineWidth = state.lineWidth
        }
        fun copy(): State {
            return State(fillStyle, strokeStyle, filter, font, lineCap, lineJoin, lineWidth)
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