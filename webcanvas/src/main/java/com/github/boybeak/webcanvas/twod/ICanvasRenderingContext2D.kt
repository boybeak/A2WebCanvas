package com.github.boybeak.webcanvas.twod

import com.github.boybeak.webcanvas.IWebCanvasContext
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

    override fun lineTo(x: Float, y: Float) = canvasPainter.lineTo(x, y)

    override fun moveTo(x: Float, y: Float) = canvasPainter.moveTo(x, y)

    override fun closePath() = canvasPainter.closePath()

    override fun rotate(angle: Float) = canvasPainter.rotate(angle)

    override fun scale(x: Float, y: Float) = canvasPainter.scale(x, y)

    override fun translate(x: Float, y: Float) = canvasPainter.translate(x, y)
}