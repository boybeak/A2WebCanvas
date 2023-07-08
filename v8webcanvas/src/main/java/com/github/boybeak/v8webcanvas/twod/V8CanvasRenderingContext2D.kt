package com.github.boybeak.v8webcanvas.twod

import android.graphics.Color
import android.graphics.Matrix
import android.util.Log
import com.eclipsesource.v8.V8
import com.eclipsesource.v8.V8Array
import com.eclipsesource.v8.V8Object
import com.eclipsesource.v8.V8TypedArray
import com.github.boybeak.v8webcanvas.V8WebCanvasView
import com.github.boybeak.v8webcanvas.image.V8HTMLImageElement
import com.github.boybeak.v8webcanvas.image.V8ImageData
import com.github.boybeak.v8webcanvas.twod.gradient.V8CanvasGradient
import com.github.boybeak.v8x.binding.Key
import com.github.boybeak.v8x.binding.V8Binding
import com.github.boybeak.v8x.binding.annotation.V8Method
import com.github.boybeak.webcanvas.image.HTMLImageElement
import com.github.boybeak.webcanvas.image.IWebImage
import com.github.boybeak.webcanvas.image.ImageData
import com.github.boybeak.webcanvas.image.WebImageManager
import com.github.boybeak.webcanvas.twod.CanvasRenderingContext2D
import com.github.boybeak.webcanvas.twod.gradient.CanvasGradient
import com.github.boybeak.webcanvas.twod.gradient.CanvasGradientManager
import com.github.boybeak.webcanvas.twod.gradient.CanvasPattern
import com.github.boybeak.webcanvas.twod.gradient.LinearGradient
import com.github.boybeak.webcanvas.twod.gradient.RadialGradient
import com.github.boybeak.webcanvas.twod.paint.Style
import java.lang.IllegalArgumentException
import kotlin.math.abs

class V8CanvasRenderingContext2D(private val v8WebCanvas2D: V8WebCanvasView) : V8Binding {

    companion object {
        private const val TAG = "V8CanvasRenderingContext2D"
    }

    private val context2D: CanvasRenderingContext2D get() = v8WebCanvas2D.getContext("2d")
    private val v8: V8 get() = v8WebCanvas2D.v8

    private val fillColorStyle = Style.ColorStyle(Color.BLACK)
    private val fillGradientStyle = Style.GradientStyle(CanvasGradient.NONE)
    private val strokeColorStyle = Style.ColorStyle(Color.BLACK)
    private val strokeGradientStyle = Style.GradientStyle(CanvasGradient.NONE)

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
            "fillStyle" -> {
                if (newValue is String) {
                    fillColorStyle.setColorStr(newValue.toString())
                    context2D.fillStyle = fillColorStyle
                } else if (newValue is V8Object) {
                    if (V8CanvasGradient.isGradient(newValue)) {
                        val gradientId = V8CanvasGradient.getId(newValue)
                        val gradient = CanvasGradientManager.getCanvasGradient(gradientId)
                        fillGradientStyle.gradient = gradient
                        context2D.fillStyle = fillGradientStyle
                    }
                }
            }
            "strokeStyle" -> {
                if (newValue is String) {
                    strokeColorStyle.setColorStr(newValue.toString())
                    context2D.strokeStyle = strokeColorStyle
                } else if (newValue is V8Object) {
                    if (V8CanvasGradient.isGradient(newValue)) {
                        val gradientId = V8CanvasGradient.getId(newValue)
                        val gradient = CanvasGradientManager.getCanvasGradient(gradientId)
                        strokeGradientStyle.gradient = gradient
                        context2D.strokeStyle = strokeGradientStyle
                    }
                }
            }
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
    fun createLinearGradient(x0: Double, y0: Double, x1: Double, y1: Double): V8Object {
        return V8CanvasGradient(context2D.createLinearGradient(x0.toFloat(), y0.toFloat(), x1.toFloat(), y1.toFloat())).getMyBinding(v8)
    }

    @V8Method
    fun createPattern(image: V8Object, repetition: String): V8Object {
        val imgId = V8HTMLImageElement.getId(image)
        val img = WebImageManager.getIWebImage<HTMLImageElement>(imgId)
        return V8CanvasGradient(context2D.createPattern(img, repetition)).getMyBinding(v8)
    }

    @V8Method
    fun createRadialGradient(
        x0: Double,
        y0: Double,
        r0: Double,
        x1: Double,
        y1: Double,
        r1: Double
    ): V8Object {
        return V8CanvasGradient(context2D.createRadialGradient(x0.toFloat(), y0.toFloat(), r0.toFloat(),
            x1.toFloat(), y1.toFloat(), r1.toFloat())).getMyBinding(v8)
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
    fun rect(x: Double, y: Double, width: Double, height: Double) {
        context2D.rect(x.toFloat(), y.toFloat(), width.toFloat(), height.toFloat())
    }

    private val roundRectRadii = FloatArray(8)
    private val roundRectRadiiReceiver1 = DoubleArray(1)    // [all-corners]
    private val roundRectRadiiReceiver2 = DoubleArray(2)    // [top-left-and-bottom-right, top-right-and-bottom-left]
    private val roundRectRadiiReceiver3 = DoubleArray(3)    // [top-left, top-right-and-bottom-left, bottom-right]
    private val roundRectRadiiReceiver4 = DoubleArray(4)    // [top-left, top-right, bottom-right, bottom-left]
    @V8Method
    fun roundRect(x: Double, y: Double, width: Double, height: Double, radii: Any) {
        roundRectRadii.fill(0F)
        when(radii) {
            is Number -> {
                roundRectRadii.fill(radii.toFloat())
            }
            is V8Array -> {
                when(radii.length()) {
                    1 ->  {
                        radii.getDoubles(0, radii.length(), roundRectRadiiReceiver1)
                        roundRectRadii.fill(roundRectRadiiReceiver1[0].toFloat())
                    }
                    2 -> {
                        radii.getDoubles(0, radii.length(), roundRectRadiiReceiver2)

                        roundRectRadii[0] = roundRectRadiiReceiver2[0].toFloat()
                        roundRectRadii[1] = roundRectRadiiReceiver2[0].toFloat()
                        roundRectRadii[2] = roundRectRadiiReceiver2[1].toFloat()
                        roundRectRadii[3] = roundRectRadiiReceiver2[1].toFloat()

                        roundRectRadii[4] = roundRectRadiiReceiver2[0].toFloat()
                        roundRectRadii[5] = roundRectRadiiReceiver2[0].toFloat()
                        roundRectRadii[6] = roundRectRadiiReceiver2[1].toFloat()
                        roundRectRadii[7] = roundRectRadiiReceiver2[1].toFloat()
                    }
                    3 -> {
                        radii.getDoubles(0, radii.length(), roundRectRadiiReceiver3)

                        roundRectRadii[0] = roundRectRadiiReceiver3[0].toFloat()
                        roundRectRadii[1] = roundRectRadiiReceiver3[0].toFloat()
                        roundRectRadii[2] = roundRectRadiiReceiver3[1].toFloat()
                        roundRectRadii[3] = roundRectRadiiReceiver3[1].toFloat()

                        roundRectRadii[4] = roundRectRadiiReceiver3[2].toFloat()
                        roundRectRadii[5] = roundRectRadiiReceiver3[2].toFloat()
                        roundRectRadii[6] = roundRectRadiiReceiver3[1].toFloat()
                        roundRectRadii[7] = roundRectRadiiReceiver3[1].toFloat()
                    }
                    4 -> {
                        radii.getDoubles(0, radii.length(), roundRectRadiiReceiver4)

                        roundRectRadii[0] = roundRectRadiiReceiver4[0].toFloat()
                        roundRectRadii[1] = roundRectRadiiReceiver4[0].toFloat()
                        roundRectRadii[2] = roundRectRadiiReceiver4[1].toFloat()
                        roundRectRadii[3] = roundRectRadiiReceiver4[1].toFloat()

                        roundRectRadii[4] = roundRectRadiiReceiver4[2].toFloat()
                        roundRectRadii[5] = roundRectRadiiReceiver4[2].toFloat()
                        roundRectRadii[6] = roundRectRadiiReceiver4[3].toFloat()
                        roundRectRadii[7] = roundRectRadiiReceiver4[3].toFloat()
                    }
                    else -> throw IllegalArgumentException("Unsupported radii size ${radii.length()}")
                }
            }
        }
        val xSymbol = if (width == 0.0) 1 else (abs(width) / width).toInt()
        val ySymbol = if (height == 0.0) 1 else (abs(height) / height).toInt()
        if (xSymbol < 0) {
            // -1, left and right should swap
            var tempX = roundRectRadii[0]
            var tempY = roundRectRadii[1]
            roundRectRadii[0] = roundRectRadii[2]
            roundRectRadii[1] = roundRectRadii[3]

            roundRectRadii[2] = tempX
            roundRectRadii[3] = tempY

            tempX = roundRectRadii[6]
            tempY = roundRectRadii[7]
            roundRectRadii[6] = roundRectRadii[4]
            roundRectRadii[7] = roundRectRadii[5]

            roundRectRadii[4] = tempX
            roundRectRadii[5] = tempY
        }
        if (ySymbol < 0) {
            // -1, top and bottom should swap
            var tempX = roundRectRadii[0]
            var tempY = roundRectRadii[1]
            roundRectRadii[0] = roundRectRadii[6]
            roundRectRadii[1] = roundRectRadii[7]

            roundRectRadii[6] = tempX
            roundRectRadii[7] = tempY

            tempX = roundRectRadii[2]
            tempY = roundRectRadii[3]
            roundRectRadii[2] = roundRectRadii[4]
            roundRectRadii[3] = roundRectRadii[5]

            roundRectRadii[4] = tempX
            roundRectRadii[5] = tempY
        }
        context2D.roundRect(x.toFloat(), y.toFloat(), width.toFloat(), height.toFloat(), roundRectRadii)
    }

    @V8Method
    fun closePath() = context2D.closePath()

    /** Transform related **/

    private val transformValues = FloatArray(9)
    @V8Method
    fun getTransform(): V8Object {
        val matrix = context2D.getTransform()
        matrix.getValues(transformValues)
        return V8Object(v8).apply {
            add("a", transformValues[3].toDouble())
            add("b", transformValues[6].toDouble())
            add("b", transformValues[5].toDouble())
            add("d", transformValues[4].toDouble())
            add("e", transformValues[7].toDouble())
            add("f", transformValues[8].toDouble())
        }
    }

    @V8Method
    fun rotate(angle: Double) = context2D.rotate(angle.toFloat())
    @V8Method
    fun scale(x: Double, y: Double) = context2D.scale(x.toFloat(), y.toFloat())
    @V8Method
    fun translate(x: Double, y: Double) = context2D.translate(x.toFloat(), y.toFloat())

    @V8Method
    fun setTransform(vararg args: Any) {
        when(args.size) {
            1 -> {
                val matrixObj = args[0] as V8Object
                context2D.setTransform(
                    matrixObj.getDouble("a").toFloat(),
                    matrixObj.getDouble("b").toFloat(),
                    matrixObj.getDouble("c").toFloat(),
                    matrixObj.getDouble("d").toFloat(),
                    matrixObj.getDouble("e").toFloat(),
                    matrixObj.getDouble("f").toFloat()
                )
            }
            6 -> {
                val a = (args[0] as Number).toFloat()
                val b = (args[1] as Number).toFloat()
                val c = (args[2] as Number).toFloat()
                val d = (args[3] as Number).toFloat()
                val e = (args[4] as Number).toFloat()
                val f = (args[5] as Number).toFloat()
                context2D.setTransform(a, b, c, d, e, f)
            }
            else -> throw IllegalArgumentException("Unsupported args count ${args.size}")
        }
    }

    @V8Method
    fun transform(a: Double, b: Double, c: Double, d: Double, e: Double, f: Double) {
        context2D.transform(a.toFloat(), b.toFloat(), c.toFloat(), d.toFloat(), e.toFloat(), f.toFloat())
    }

    @V8Method
    fun resetTransform() {
        context2D.resetTransform()
    }

    /** Image related **/

    @V8Method
    fun drawImage(vararg args: Any) {
        when(args.size) {
            3 -> {
                val image = args[0] as V8Object
                val dx = (args[1] as Number).toInt()
                val dy = (args[2] as Number).toInt()

                val id = V8HTMLImageElement.getId(image)
                val img = WebImageManager[id]
                context2D.drawImage(img, dx, dy)
            }
            5 -> {
                val image = args[0] as V8Object
                val dx = (args[1] as Number).toInt()
                val dy = (args[2] as Number).toInt()
                val dw = (args[3] as Number).toInt()
                val dh = (args[4] as Number).toInt()

                val id = V8HTMLImageElement.getId(image)
                val img = WebImageManager[id]
                context2D.drawImage(img, dx, dy, dw, dh)
            }
            9 -> {
                val image = args[0] as V8Object
                val sx = (args[1] as Number).toInt()
                val sy = (args[2] as Number).toInt()
                val sw = (args[3] as Number).toInt()
                val sh = (args[4] as Number).toInt()

                val dx = (args[5] as Number).toInt()
                val dy = (args[6] as Number).toInt()
                val dw = (args[7] as Number).toInt()
                val dh = (args[8] as Number).toInt()

                val id = V8HTMLImageElement.getId(image)
                val img = WebImageManager[id]
                context2D.drawImage(img, sx, sy, sw, sh, dx, dy, dw, dh)
            }
            else -> throw IllegalArgumentException("Unsupported arguments count ${args.size}")
        }

    }

    @V8Method
    fun getImageData(vararg args: Any): V8Object {
        return when(args.size) {
            4 -> {
                val sx = (args[0] as Number).toInt()
                val sy = (args[1] as Number).toInt()
                val sw = (args[2] as Number).toInt()
                val sh = (args[3] as Number).toInt()
                val imgData = context2D.getImageData(sx, sy, sw, sh)
                V8ImageData(imgData).getMyBinding(v8)
            }
            9 -> {
                TODO("Not support yet")
            }
            else -> throw IllegalArgumentException("Unsupported arguments count ${args.size}")
        }
    }

    @V8Method
    fun putImageData(vararg args: Any) {
        when(args.size) {
            3 -> {
                val v8ImgData = args[0] as V8Object
                val imgId = V8ImageData.getId(v8ImgData)
                val imageData = WebImageManager.getIWebImage<ImageData>(imgId)
                val dx = (args[1] as Number).toInt()
                val dy = (args[2] as Number).toInt()

                context2D.putImageData(imageData, dx, dy)
            }
            7 -> {
                val v8ImgData = args[0] as V8Object
                val imgId = V8ImageData.getId(v8ImgData)
                val imageData = WebImageManager.getIWebImage<ImageData>(imgId)
                val dx = (args[1] as Number).toInt()
                val dy = (args[2] as Number).toInt()
                val dirtyX = (args[3] as Number).toInt()
                val dirtyY = (args[4] as Number).toInt()
                val dirtyWidth = (args[5] as Number).toInt()
                val dirtyHeight = (args[6] as Number).toInt()

                context2D.putImageData(imageData, dx, dy, dirtyX, dirtyY, dirtyWidth, dirtyHeight)
            }
            else -> throw IllegalArgumentException("Unsupported arguments count ${args.size}")
        }
    }

}