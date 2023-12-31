package com.github.boybeak.canvas._2d

import android.graphics.ColorSpace
import android.graphics.Matrix
import android.util.Log
import com.github.boybeak.canvas.IWebCanvasContext
import com.github.boybeak.canvas._2d.gradient.CanvasPattern
import com.github.boybeak.canvas._2d.gradient.LinearGradient
import com.github.boybeak.canvas._2d.gradient.RadialGradient
import com.github.boybeak.canvas._2d.paint.Style
import com.github.boybeak.canvas._2d.paint.TextMetrics
import com.github.boybeak.canvas.image.IWebImage
import com.github.boybeak.canvas.image.ImageData
import com.github.boybeak.canvas.painter.IAndroidCanvasProvider
import com.github.boybeak.canvas.painter.IPainter2D

interface IWebCanvasContext2D : IWebCanvasContext, IPainter2D, IAndroidCanvasProvider {

    companion object {
        private const val TAG = "IWebCanvasContext2D"
    }

    val canvasPainter: IPainter2D

    override var fillStyle: Style
        get() = canvasPainter.fillStyle
        set(value) { canvasPainter.fillStyle = value }
    override var strokeStyle: Style
        get() = canvasPainter.strokeStyle
        set(value) { canvasPainter.strokeStyle = value }
    override var filter: String?
        get() = canvasPainter.filter
        set(value) { canvasPainter.filter = value }
    override var font: String
        get() = canvasPainter.font
        set(value) { canvasPainter.font = value }
    override var globalAlpha: Float
        get() = canvasPainter.globalAlpha
        set(value) { canvasPainter.globalAlpha = value }
    override var globalCompositeOperation: String
        get() = canvasPainter.globalCompositeOperation
        set(value) { canvasPainter.globalCompositeOperation = value }
    override var lineCap: String
        get() = canvasPainter.lineCap
        set(value) { canvasPainter.lineCap = value }
    override var lineJoin: String
        get() = canvasPainter.lineJoin
        set(value) { canvasPainter.lineJoin = value }
    override var lineWidth: Float
        get() = canvasPainter.lineWidth
        set(value) { canvasPainter.lineWidth = value }
    override var miterLimit: Float
        get() = canvasPainter.miterLimit
        set(value) { canvasPainter.miterLimit = value }
    override var shadowBlur: Float
        get() = canvasPainter.shadowBlur
        set(value) { canvasPainter.shadowBlur = value }
    override var shadowColor: String?
        get() = canvasPainter.shadowColor
        set(value) { canvasPainter.shadowColor = value }
    override var shadowOffsetX: Float
        get() = canvasPainter.shadowOffsetX
        set(value) { canvasPainter.shadowOffsetX = value }
    override var shadowOffsetY: Float
        get() = canvasPainter.shadowOffsetY
        set(value) { canvasPainter.shadowOffsetY = value }
    override var textAlign: String
        get() = canvasPainter.textAlign
        set(value) { canvasPainter.textAlign = value }
    override var textBaseline: String
        get() = canvasPainter.textBaseline
        set(value) { canvasPainter.textBaseline = value }

    override fun createLinearGradient(x0: Float, y0: Float, x1: Float, y1: Float): LinearGradient {
        return canvasPainter.createLinearGradient(x0, y0, x1, y1)
    }

    override fun createPattern(image: IWebImage, repetition: String): CanvasPattern {
        return canvasPainter.createPattern(image, repetition)
    }

    override fun createRadialGradient(
        x0: Float,
        y0: Float,
        r0: Float,
        x1: Float,
        y1: Float,
        r1: Float
    ): RadialGradient {
        return canvasPainter.createRadialGradient(x0, y0, r0, x1, y1, r1)
    }

    override fun save() = canvasPainter.save()

    override fun restore() = canvasPainter.restore()

    override fun reset() = canvasPainter.reset()

    override fun clearRect(x: Float, y: Float, width: Float, height: Float) = canvasPainter.clearRect(x, y, width, height)

    override fun fill() = canvasPainter.fill()

    override fun fillRect(x: Float, y: Float, width: Float, height: Float) = canvasPainter.fillRect(x, y, width, height)

    override fun fillText(text: String, x: Float, y: Float) = canvasPainter.fillText(text, x, y)

    override fun stroke() {
        canvasPainter.stroke()
    }

    override fun strokeRect(x: Float, y: Float, width: Float, height: Float) = canvasPainter.strokeRect(x, y, width, height)

    override fun strokeText(text: String, x: Float, y: Float) = canvasPainter.strokeText(text, x, y)

    override fun measureText(text: String): TextMetrics = canvasPainter.measureText(text)

    override fun beginPath() {
        canvasPainter.beginPath()
    }

    override fun arc(
        x: Float,
        y: Float,
        radius: Float,
        startAngle: Float,
        endAngle: Float,
        counterclockwise: Boolean
    ) = canvasPainter.arc(x, y, radius, startAngle, endAngle, counterclockwise)

    override fun arcTo(x1: Float, y1: Float, x2: Float, y2: Float, radius: Float) = canvasPainter.arcTo(x1, y1, x2, y2, radius)

    override fun bezierCurveTo(
        cp1x: Float,
        cp1y: Float,
        cp2x: Float,
        cp2y: Float,
        x: Float,
        y: Float
    )  = canvasPainter.bezierCurveTo(cp1x, cp1y, cp2x, cp2y, x, y)

    override fun lineTo(x: Float, y: Float) = canvasPainter.lineTo(x, y)

    override fun moveTo(x: Float, y: Float) = canvasPainter.moveTo(x, y)

    override fun quadraticCurveTo(cpx: Float, cpy: Float, x: Float, y: Float) = canvasPainter.quadraticCurveTo(cpx, cpy, x, y)

    override fun rect(x: Float, y: Float, width: Float, height: Float) {
        canvasPainter.rect(x, y, width, height)
    }

    override fun roundRect(x: Float, y: Float, width: Float, height: Float, radii: FloatArray) {
        canvasPainter.roundRect(x, y, width, height, radii)
    }

    override fun clip() {
        canvasPainter.clip()
    }

    override fun closePath() = canvasPainter.closePath()

    override fun getTransform(): Matrix {
        return canvasPainter.getTransform()
    }

    override fun rotate(angle: Float) = canvasPainter.rotate(angle)

    override fun scale(x: Float, y: Float) = canvasPainter.scale(x, y)

    override fun translate(x: Float, y: Float) = canvasPainter.translate(x, y)

    override fun setTransform(a: Float, b: Float, c: Float, d: Float, e: Float, f: Float) {
        canvasPainter.setTransform(a, b, c, d, e, f)
    }

    override fun setTransform(matrix: Matrix) {
        canvasPainter.setTransform(matrix)
    }

    override fun transform(a: Float, b: Float, c: Float, d: Float, e: Float, f: Float) {
        canvasPainter.transform(a, b, c, d, e, f)
    }

    override fun resetTransform() {
        canvasPainter.resetTransform()
    }

    override fun drawImage(image: IWebImage, dx: Int, dy: Int) = canvasPainter.drawImage(image, dx, dy)

    override fun drawImage(image: IWebImage, dx: Int, dy: Int, dWidth: Int, dHeight: Int) = canvasPainter.drawImage(image, dx, dy, dWidth, dHeight)

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
    ) = canvasPainter.drawImage(image, sx, sy, sWidth, sHeight, dx, dy, dWidth, dHeight)

    override fun getImageData(sx: Int, sy: Int, sw: Int, sh: Int): ImageData {
        return canvasPainter.getImageData(sx, sy, sw, sh)
    }

    override fun getImageData(
        sx: Int,
        sy: Int,
        sw: Int,
        sh: Int,
        colorSpace: ColorSpace
    ): ImageData {
        TODO("Not yet implemented")
    }

    override fun putImageData(imageData: ImageData, dx: Int, dy: Int) {
        canvasPainter.putImageData(imageData, dx, dy)
    }

    override fun putImageData(
        imageData: ImageData,
        dx: Int,
        dy: Int,
        dirtyX: Int,
        dirtyY: Int,
        dirtyWidth: Int,
        dirtyHeight: Int
    ) {
        canvasPainter.putImageData(imageData, dx, dy, dirtyX, dirtyY, dirtyWidth, dirtyHeight)
    }
}