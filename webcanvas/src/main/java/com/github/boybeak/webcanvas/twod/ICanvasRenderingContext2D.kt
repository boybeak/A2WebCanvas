package com.github.boybeak.webcanvas.twod

import android.graphics.ColorSpace
import android.graphics.Matrix
import com.github.boybeak.webcanvas.IWebCanvasContext
import com.github.boybeak.webcanvas.image.IWebImage
import com.github.boybeak.webcanvas.image.ImageData
import com.github.boybeak.webcanvas.twod.paint.TextMetrics

interface ICanvasRenderingContext2D : IWebCanvasContext, ICanvasPainter2D {

    val canvasPainter: ICanvasPainter2D
    override var fillStyle: String
        get() = canvasPainter.fillStyle
        set(value) { canvasPainter.fillStyle = value }
    override var strokeStyle: String
        get() = canvasPainter.strokeStyle
        set(value) { canvasPainter.strokeStyle = value }
    override var filter: String?
        get() = canvasPainter.filter
        set(value) { canvasPainter.filter = value }
    override var font: String
        get() = canvasPainter.font
        set(value) { canvasPainter.font = value }
    override var lineCap: String
        get() = canvasPainter.lineCap
        set(value) { canvasPainter.lineCap = value }
    override var lineJoin: String
        get() = canvasPainter.lineJoin
        set(value) { canvasPainter.lineJoin = value }
    override var lineWidth: Float
        get() = canvasPainter.lineWidth
        set(value) { canvasPainter.lineWidth = value }
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

    override fun save() = canvasPainter.save()

    override fun restore() = canvasPainter.restore()

    override fun reset() = canvasPainter.reset()

    override fun clearRect(x: Float, y: Float, width: Float, height: Float) = canvasPainter.clearRect(x, y, width, height)

    override fun fill() = canvasPainter.fill()

    override fun fillRect(x: Float, y: Float, width: Float, height: Float) = canvasPainter.fillRect(x, y, width, height)

    override fun fillText(text: String, x: Float, y: Float) = canvasPainter.fillText(text, x, y)

    override fun stroke() = canvasPainter.stroke()

    override fun strokeRect(x: Float, y: Float, width: Float, height: Float) = canvasPainter.strokeRect(x, y, width, height)

    override fun strokeText(text: String, x: Float, y: Float) = canvasPainter.strokeText(text, x, y)

    override fun measureText(text: String): TextMetrics = canvasPainter.measureText(text)

    override fun beginPath() = canvasPainter.beginPath()

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