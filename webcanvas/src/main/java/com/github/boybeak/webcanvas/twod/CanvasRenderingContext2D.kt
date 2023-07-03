package com.github.boybeak.webcanvas.twod

import android.graphics.ColorSpace
import com.github.boybeak.webcanvas.render.AbsRenderer2D
import com.github.boybeak.webcanvas.render.Renderer2D
import com.github.boybeak.webcanvas.image.IWebImage

class CanvasRenderingContext2D internal constructor(iWebCanvas: IWebCanvas2D) : AbsCanvasRenderingContext2D() {

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

    override fun drawImage(image: IWebImage, dx: Int, dy: Int) {
        super.drawImage(image, dx, dy)
        postInvalidate()
    }

    override fun drawImage(image: IWebImage, dx: Int, dy: Int, dWidth: Int, dHeight: Int) {
        super.drawImage(image, dx, dy, dWidth, dHeight)
        postInvalidate()
    }

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
    ) {
        super.drawImage(image, sx, sy, sWidth, sHeight, dx, dy, dWidth, dHeight)
        postInvalidate()
    }

    override fun getImageData(sx: Float, sy: Float, sw: Float, sh: Float) {
        TODO("Not yet implemented")
    }

    override fun getImageData(sx: Float, sy: Float, sw: Float, sh: Float, colorSpace: ColorSpace) {
        TODO("Not yet implemented")
    }
}