package com.github.boybeak.webcanvas.twod

import android.graphics.ColorSpace
import android.graphics.Matrix
import com.github.boybeak.webcanvas.image.IWebImage
import com.github.boybeak.webcanvas.image.ImageData
import com.github.boybeak.webcanvas.twod.gradient.CanvasPattern
import com.github.boybeak.webcanvas.twod.gradient.LinearGradient
import com.github.boybeak.webcanvas.twod.gradient.RadialGradient
import com.github.boybeak.webcanvas.twod.paint.Style
import com.github.boybeak.webcanvas.twod.paint.TextMetrics

interface ICanvasPainter2D {

    var fillStyle: Style
    var strokeStyle: Style

    var filter: String?

    var font: String

    var globalAlpha: Float
    var globalCompositeOperation: String

    var lineCap: String
    var lineJoin: String
    var lineWidth: Float

    var shadowBlur: Float
    var shadowColor: String?
    var shadowOffsetX: Float
    var shadowOffsetY: Float

    var textAlign: String
    var textBaseline: String

    fun createLinearGradient(x0: Float, y0: Float, x1: Float, y1: Float): LinearGradient
    fun createPattern(image: IWebImage, repetition: String): CanvasPattern
    fun createRadialGradient(x0: Float, y0: Float, r0: Float, x1: Float, y1: Float, r1: Float): RadialGradient

    fun save()
    fun restore()

    fun reset()

    fun clearRect(x: Float, y: Float, width: Float, height: Float)

    fun fill()
    fun fillRect(x: Float, y: Float, width: Float, height: Float)
    fun fillText(text: String, x: Float, y: Float)

    fun stroke()
    fun strokeRect(x: Float, y: Float, width: Float, height: Float)
    fun strokeText(text: String, x: Float, y: Float)

    fun measureText(text: String): TextMetrics

    /** Path related **/
    fun beginPath()
    fun arc(x: Float, y: Float, radius: Float, startAngle: Float, endAngle: Float, counterclockwise: Boolean = false)
    fun arcTo(x1: Float, y1: Float, x2: Float, y2: Float, radius: Float)
    fun bezierCurveTo(cp1x: Float, cp1y: Float, cp2x: Float, cp2y: Float, x: Float, y: Float)
    fun lineTo(x: Float, y: Float)
    fun moveTo(x: Float, y: Float)
    fun quadraticCurveTo(cpx: Float, cpy: Float, x: Float, y: Float)
    fun rect(x: Float, y: Float, width: Float, height: Float)
    fun roundRect(x: Float, y: Float, width: Float, height: Float, radii: FloatArray)

    fun closePath()

    /** Transform related **/

    fun getTransform(): Matrix

    fun rotate(angle: Float)
    fun scale(x: Float, y: Float)
    fun translate(x: Float, y: Float)

    /**
     * a - scaleX
     * b - skewY
     * c - skewX
     * d - scaleY
     * e - transformX
     * f - transformY
     */
    fun setTransform(a: Float, b: Float, c: Float, d: Float, e: Float, f: Float)
    fun setTransform(matrix: Matrix)

    /**
     * a - scaleX
     * b - skewY
     * c - skewX
     * d - scaleY
     * e - transformX
     * f - transformY
     */
    fun transform(a: Float, b: Float, c: Float, d: Float, e: Float, f: Float)

    fun resetTransform()

    /** Image related **/

    fun drawImage(image: IWebImage, dx: Int, dy: Int)
    fun drawImage(image: IWebImage, dx: Int, dy: Int, dWidth: Int, dHeight: Int)
    fun drawImage(image: IWebImage, sx: Int, sy: Int, sWidth: Int, sHeight: Int, dx: Int, dy: Int, dWidth: Int, dHeight: Int)
    fun getImageData(sx: Int, sy: Int, sw: Int, sh: Int): ImageData
    fun getImageData(sx: Int, sy: Int, sw: Int, sh: Int, colorSpace: ColorSpace): ImageData

    fun putImageData(imageData: ImageData, dx: Int, dy: Int)
    fun putImageData(imageData: ImageData, dx: Int, dy: Int, dirtyX: Int, dirtyY: Int, dirtyWidth: Int, dirtyHeight: Int)
}