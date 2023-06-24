package com.github.boybeak.webcanvas

import android.content.Context
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.github.boybeak.webcanvas.twod.CanvasRenderingContext2D
import com.github.boybeak.webcanvas.twod.IWebCanvas2D
import com.github.boybeak.webcanvas.webgl.IWebCanvasWebGL
import java.lang.IllegalArgumentException

class WebCanvasView : SurfaceView, IWebCanvas2D, IWebCanvasWebGL {

    companion object {
        private const val TAG = "WebCanvasView"
    }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private var canvasContext: IWebCanvasContext? = null
    private var renderMode: Int = IWebCanvas.RENDER_MODE_WHEN_DIRTY

    override val surfaceHolder: SurfaceHolder
        get() = holder

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
        }
        // Do some init
        canvasContext?.onRenderModeChanged(renderMode)
        @Suppress("UNCHECKED_CAST")
        return canvasContext as T
    }

    override fun getPlatformContext(): Context {
        return context
    }

    fun setRenderMode(@RenderMode mode: Int) {
        this.renderMode = mode
        canvasContext?.onRenderModeChanged(mode)
    }

}