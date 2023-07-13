package com.github.boybeak.webcanvas

import android.content.Context
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.github.boybeak.webcanvas.twod.CanvasRenderingContext2D
import com.github.boybeak.webcanvas.twod.IWebCanvas2D
import com.github.boybeak.webcanvas.webgl.IWebCanvasWebGL
import java.lang.IllegalArgumentException

typealias OnPrepareListener = () -> Looper?

open class WebCanvasView : SurfaceView, IWebCanvas2D, IWebCanvasWebGL {

    companion object {
        private const val TAG = "WebCanvasView"
    }

    private var canvasContext: IWebCanvasContext? = null
    private var renderMode: Int = IWebCanvas.RENDER_MODE_WHEN_DIRTY

    override val surfaceHolder: SurfaceHolder
        get() = holder

    private var renderLooperInner: Looper? = null
    private val renderLooper: Looper get() {
        if (renderLooperInner == null) {
            renderLooperInner = onPrepareListener?.invoke() ?: createDefaultLooper()
        }
        return renderLooperInner!!
    }

    private var renderHandler: Handler? = null
        get() {
            if (field == null) {
                field = Handler(renderLooper)
            }
            return field
        }

    private var onPrepareListener: OnPrepareListener? = null

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private fun createDefaultLooper(): Looper {
        return HandlerThread("RenderThread").also {
            it.start()
        }.looper
    }

    fun start(callback: () -> Looper?) {

    }

    fun stop(callback: (looper: Looper) -> Unit) {

    }

    fun setOnPrepareCallback(onPrepare: OnPrepareListener) {
        this.onPrepareListener = onPrepare
    }

    override fun <T : IWebCanvasContext> getContext(type: String): T {
        if (canvasContext == null) {
            canvasContext = when {
                "2d".equals(type, true) -> {
                    CanvasRenderingContext2D(this)
                }
                "webgl".equals(type, true) -> {
                    TODO("WebGL not supported yet")
                }
                "webgl2".equals(type, true) -> {
                    TODO("WebGL not supported yet")
                }
                else -> {
                    throw IllegalArgumentException("")
                }
            }
            // Do some init
            canvasContext?.onRenderModeChanged(renderMode)
        }
        @Suppress("UNCHECKED_CAST")
        return canvasContext as T
    }

    override fun getPlatformContext(): Context {
        return context
    }

    override fun queueEvent(event: Runnable) {
        renderHandler?.post(event)
    }

    override fun queueEvent(delayInMills: Long, event: Runnable) {
        renderHandler?.postDelayed(event, delayInMills)
    }

    override fun removeEvent(event: Runnable) {
        renderHandler?.removeCallbacks(event)
    }

    fun setRenderMode(@RenderMode mode: Int) {
        this.renderMode = mode
        canvasContext?.onRenderModeChanged(mode)
    }

    fun requestRender() {
        canvasContext?.requestRender()
    }

    fun onResume() {
        canvasContext?.onResume()
    }
    fun onPause() {
        canvasContext?.onPause()
    }

}