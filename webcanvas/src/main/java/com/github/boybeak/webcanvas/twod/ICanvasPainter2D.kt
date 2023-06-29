package com.github.boybeak.webcanvas.twod

import com.github.boybeak.webcanvas.twod.paint.TextMetrics

interface ICanvasPainter2D {

    var fillStyle: String
    var strokeStyle: String

    var filter: String?

    var font: String

    var lineCap: String
    var lineJoin: String
    var lineWidth: Float

    var shadowBlur: Float
    var shadowColor: String?
    var shadowOffsetX: Float
    var shadowOffsetY: Float

    var textAlign: String
    var textBaseline: String

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
    fun lineTo(x: Float, y: Float)
    fun moveTo(x: Float, y: Float)
    fun closePath()


    fun rotate(angle: Float)
    fun scale(x: Float, y: Float)
    fun translate(x: Float, y: Float)
}