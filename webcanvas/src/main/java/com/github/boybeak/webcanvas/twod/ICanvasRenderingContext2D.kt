package com.github.boybeak.webcanvas.twod

import com.github.boybeak.webcanvas.IWebCanvasContext

interface ICanvasRenderingContext2D : IWebCanvasContext {

    var fillStyle: String
    var strokeStyle: String

    var filter: String?

    var font: String

    var lineCap: String
    var lineJoin: String
    var lineWidth: Float

    fun save()
    fun restore()

    fun clearRect(x: Float, y: Float, width: Float, height: Float)

    fun fill()
    fun fillRect(x: Float, y: Float, width: Float, height: Float)
    fun fillText(text: String, x: Float, y: Float)

    fun stroke()
    fun strokeRect(x: Float, y: Float, width: Float, height: Float)
    fun strokeText(text: String, x: Float, y: Float)

    /** Path related **/
    fun beginPath()
    fun lineTo(x: Float, y: Float)
    fun moveTo(x: Float, y: Float)
    fun closePath()

}