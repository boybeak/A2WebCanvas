package com.github.boybeak.v8webcanvas.twod

import android.util.Log
import com.eclipsesource.v8.V8
import com.eclipsesource.v8.V8Object
import com.github.boybeak.v8webcanvas.V8WebCanvasView
import com.github.boybeak.v8x.binding.Key
import com.github.boybeak.v8x.binding.V8Binding
import com.github.boybeak.v8x.binding.annotation.V8Method
import com.github.boybeak.webcanvas.twod.CanvasRenderingContext2D
import java.lang.IllegalArgumentException

class V8CanvasRenderingContext2D(private val v8WebCanvas2D: V8WebCanvasView) : V8Binding {

    companion object {
        private const val TAG = "V8CanvasRenderingContext2D"
    }

    private val context2D: CanvasRenderingContext2D get() = v8WebCanvas2D.getContext("2d")
    private val v8: V8 get() = v8WebCanvas2D.v8

    override fun getBindingId(): String {
        return hashCode().toString()
    }

    override fun onBindingChanged(
        target: V8Object,
        fieldInfo: Key,
        newValue: Any?,
        oldValue: Any?
    ) {}

    override fun getAdditionalPropertyNames(): Array<String> {
        return arrayOf(
            "fillStyle", "strokeStyle", "filter", "font",
            "lineCap", "lineJoin", "lineWidth",
            "shadowBlur", "shadowColor", "shadowOffsetX", "shadowOffsetY",
            "textAlign", "textBaseline"
        )
    }

    override fun onAdditionalPropertyChanged(
        target: V8Object,
        propertyName: String,
        newValue: Any?,
        oldValue: Any?
    ) {
        when(propertyName) {
            "fillStyle" -> context2D.fillStyle = newValue.toString()
            "strokeStyle" -> context2D.strokeStyle = newValue.toString()
            "filter" -> context2D.filter = newValue.toString()
            "font" -> context2D.font = newValue.toString()
            "lineCap" -> context2D.lineCap = newValue.toString()
            "lineJoin" -> context2D.lineJoin = newValue.toString()
            "lineWidth" -> context2D.lineWidth = (newValue as Number).toFloat()
            "shadowBlur" -> context2D.shadowBlur = (newValue as Number).toFloat()
            "shadowColor" -> context2D.shadowColor = newValue.toString()
            "shadowOffsetX" -> context2D.shadowOffsetX = (newValue as Number).toFloat()
            "shadowOffsetY" -> context2D.shadowOffsetY = (newValue as Number).toFloat()
            "textAlign" -> context2D.textAlign = newValue.toString()
            "textBaseline" -> context2D.textBaseline = newValue.toString()
        }
    }

    @V8Method
    fun save() = context2D.save()
    
    @V8Method
    fun restore() = context2D.restore()

    @V8Method
    fun reset() = context2D.reset()

    @V8Method
    fun clearRect(x: Double, y: Double, width: Double, height: Double) {
        context2D.clearRect(x.toFloat(), y.toFloat(), width.toFloat(), height.toFloat())
    }

    @V8Method
    fun fill() = context2D.fill()

    @V8Method
    fun fillRect(x: Double, y: Double, w: Double, h: Double) {
        context2D.fillRect(x.toFloat(), y.toFloat(), w.toFloat(), h.toFloat())
    }
    @V8Method
    fun fillText(text: String, x: Double, y: Double) {
        context2D.fillText(text, x.toFloat(), y.toFloat())
    }

    @V8Method
    fun stroke() = context2D.stroke()
    @V8Method
    fun strokeRect(x: Double, y: Double, width: Double, height: Double) {
        context2D.strokeRect(x.toFloat(), y.toFloat(), width.toFloat(), height.toFloat())
    }
    @V8Method
    fun strokeText(text: String, x: Double, y: Double) {
        context2D.strokeText(text, x.toFloat(), y.toFloat())
    }

    @V8Method
    fun measureText(text: String): V8Object {
        return V8Object(v8).apply {
            val tm = context2D.measureText(text)
            add("width", tm.width.toDouble())
            add("actualBoundingBoxAscent", tm.actualBoundingBoxAscent.toDouble())
            add("actualBoundingBoxDescent", tm.actualBoundingBoxDescent.toDouble())
            add("fontBoundingBoxAscent", tm.fontBoundingBoxAscent.toDouble())
            add("fontBoundingBoxDescent", tm.fontBoundingBoxDescent.toDouble())
        }
    }

    /** Path related **/
    @V8Method
    fun beginPath() = context2D.beginPath()

    @V8Method
    fun arc(vararg args: Any) {
        when(args.size) {
            5 -> {
                val x = (args[0] as Number).toFloat()
                val y = (args[1] as Number).toFloat()
                val radius = (args[2] as Number).toFloat()
                val startAngle = (args[3] as Number).toFloat()
                val endAngle = (args[4] as Number).toFloat()
                context2D.arc(x, y, radius, startAngle, endAngle)
            }
            6 -> {
                val x = (args[0] as Number).toFloat()
                val y = (args[1] as Number).toFloat()
                val radius = (args[2] as Number).toFloat()
                val startAngle = (args[3] as Number).toFloat()
                val endAngle = (args[4] as Number).toFloat()
                val counterclockwise = args[5] as Boolean
                context2D.arc(x, y, radius, startAngle, endAngle, counterclockwise)
            }
            else -> throw IllegalArgumentException("Wrong arguments size(${args.size})")
        }
    }
    @V8Method
    fun arcTo(x1: Double, y1: Double, x2: Double, y2: Double, radius: Double) {
        context2D.arcTo(x1.toFloat(), y1.toFloat(), x2.toFloat(), y2.toFloat(), radius.toFloat())
    }
    @V8Method
    fun bezierCurveTo(cp1x: Double, cp1y: Double, cp2x: Double, cp2y: Double, x: Double, y: Double) {
        context2D.bezierCurveTo(cp1x.toFloat(), cp1y.toFloat(), cp2x.toFloat(), cp2y.toFloat(), x.toFloat(), y.toFloat())
    }
    @V8Method
    fun lineTo(x: Double, y: Double) = context2D.lineTo(x.toFloat(), y.toFloat())
    @V8Method
    fun moveTo(x: Double, y: Double) = context2D.moveTo(x.toFloat(), y.toFloat())
    @V8Method
    fun quadraticCurveTo(cpx: Double, cpy: Double, x: Double, y: Double) = context2D.quadraticCurveTo(cpx.toFloat(), cpy.toFloat(), x.toFloat(), y.toFloat())
    @V8Method
    fun closePath() = context2D.closePath()

    /** Transform related **/

    @V8Method
    fun rotate(angle: Double) = context2D.rotate(angle.toFloat())
    @V8Method
    fun scale(x: Double, y: Double) = context2D.scale(x.toFloat(), y.toFloat())
    @V8Method
    fun translate(x: Double, y: Double) = context2D.translate(x.toFloat(), y.toFloat())

    /** Image related **/

    @V8Method
    fun drawImage(image: V8Object, dx: Int, dy: Int) {
        TODO()
    }
    @V8Method
    fun drawImage(image: V8Object, dx: Int, dy: Int, dWidth: Int, dHeight: Int) {
        TODO()
    }
    @V8Method
    fun drawImage(image: V8Object, sx: Int, sy: Int, sWidth: Int, sHeight: Int, dx: Int, dy: Int, dWidth: Int, dHeight: Int) {
        TODO()
    }

}