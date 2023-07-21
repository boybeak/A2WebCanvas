package com.github.boybeak.canvas.context

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.RenderNode
import android.util.Log
import com.github.boybeak.canvas._2d.IWebCanvasContext2D
import com.github.boybeak.canvas.image.IWebImage
import com.github.boybeak.canvas.image.ImageData
import com.github.boybeak.canvas.onscreen.AbsWebCanvasContextOnscreen
import com.github.boybeak.canvas.onscreen.IWebCanvasContextOnscreen
import com.github.boybeak.canvas.onscreen.IWebCanvasOnscreen
import com.github.boybeak.canvas.painter.IPainter2D
import com.github.boybeak.canvas.painter.Painter2D
import com.github.boybeak.canvas.render.RenderExecutor
import com.github.boybeak.canvas.render.RenderMode

class WebCanvasContextOnscreen2D constructor(canvas: IWebCanvasOnscreen) : AbsWebCanvasContextOnscreen(canvas), IWebCanvasContext2D {

    companion object {
        private const val TAG = "WebCanvasContextOnscreen2D"
    }

    override val canvasPainter: IPainter2D = Painter2D(this)

    private val androidContext get() = canvas.androidContext

    private var pid = 0L

    private var canvasInner: Canvas? = null
        get() {
            if (field == null) {
                field = canvas.surfaceHolder.lockCanvas().apply {
                    onCreateCanvas(this)
                }
            }
            return field!!
        }

    override val androidCanvas: Canvas
        get() = canvasInner!!

    fun draw(onDraw: IWebCanvasContext2D.() -> Unit) {
        post {
            onDraw.invoke(this)
        }
    }

    private fun onCreateCanvas(canvas: Canvas) {
        pid = Thread.currentThread().id
        canvas.drawColor(Color.WHITE)
        canvas.scale(androidContext.resources.displayMetrics.density, androidContext.resources.displayMetrics.density)
    }

    override fun onFrameRender() {
        Log.d(TAG, "onFrameRender ")
        if (canvasInner != null) {
            if (Thread.currentThread().id != pid) {
                Log.e(TAG, "onFrameRender render in wrong thread")
            }
            canvas.surfaceHolder.unlockCanvasAndPost(canvasInner!!)
            canvasInner = null
        }
    }

    override fun onStopRender() {
        Log.d(TAG, "onStopRender ------>")
        if (canvasInner != null) {
            if (Thread.currentThread().id != pid) {
                Log.e(TAG, "onStopRender render in wrong thread")
            }
            canvas.surfaceHolder.unlockCanvasAndPost(canvasInner!!)
            canvasInner = null
        }
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

    override fun reset() {
        super.reset()
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

    override fun putImageData(imageData: ImageData, dx: Int, dy: Int) {
        super.putImageData(imageData, dx, dy)
        postInvalidate()
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
        super.putImageData(imageData, dx, dy, dirtyX, dirtyY, dirtyWidth, dirtyHeight)
        postInvalidate()
    }

    private fun postInvalidate() {
        if (getRenderMode() == RenderMode.RENDER_MODE_AUTO) {
            requestRender()
        }
    }

}