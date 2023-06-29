package com.github.boybeak.webcanvas.twod

import com.github.boybeak.webcanvas.render.AbsRenderer2D
import com.github.boybeak.webcanvas.render.Renderer2D

class CanvasRenderingContext2D(iWebCanvas: IWebCanvas2D) : AbsCanvasRenderingContext2D() {

    companion object {
        private const val TAG = "CanvasRenderingContext2D"
    }

    override val renderer: AbsRenderer2D = Renderer2D(iWebCanvas)
    override val canvasPainter = CanvasPainter2D(renderer)

    override fun reset() {
        super.reset()
        postInvalidate()
    }

    override fun clearRect(x: Float, y: Float, width: Float, height: Float) {
        super.clearRect(x, y, width, height)
        postInvalidate()
    }

    override fun fill() {
        super.fill()
        postInvalidate()
    }

    override fun fillRect(x: Float, y: Float, width: Float, height: Float) {
        super.fillRect(x, y, width, height)
        postInvalidate()
    }

    override fun fillText(text: String, x: Float, y: Float) {
        super.fillText(text, x, y)
        postInvalidate()
    }

    override fun stroke() {
        super.stroke()
        postInvalidate()
    }
    override fun strokeRect(x: Float, y: Float, width: Float, height: Float) {
        super.strokeRect(x, y, width, height)
        postInvalidate()
    }

    override fun strokeText(text: String, x: Float, y: Float) {
        super.strokeText(text, x, y)
        postInvalidate()
    }

}