package com.github.boybeak.webcanvas.twod

import com.github.boybeak.webcanvas.IWebCanvasContext

interface ICanvasRenderingContext2D : IWebCanvasContext {
    fun strokeRect(x: Float, y: Float, width: Float, height: Float)
    fun fillRect(x: Float, y: Float, width: Float, height: Float)
    fun beginPath()
    fun moveTo(x: Float, y: Float)
    fun lineTo(x: Float, y: Float)
    fun closePath()
    fun stroke()
}