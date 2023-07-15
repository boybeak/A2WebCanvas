package com.github.boybeak.canvas

import android.content.Context
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.github.boybeak.canvas.context.WebCanvasContextOnscreen2D
import com.github.boybeak.canvas.onscreen.IWebCanvasContextOnscreen
import com.github.boybeak.canvas.onscreen.IWebCanvasOnscreen
import com.github.boybeak.canvas.render.RenderExecutor

class WebCanvasOnscreen : SurfaceView, IWebCanvasOnscreen {

    companion object {
        private const val TAG = "WebCanvasOnscreen"
    }

    private var contextOnscreen: IWebCanvasContextOnscreen? = null

    override val surfaceHolder: SurfaceHolder
        get() = holder

    private var myLooper: Looper? = null
    override val looper: Looper get() = myLooper!!

    override val androidContext: Context
        get() = context
    override val isStarted: Boolean
        get() = myLooper != null

    private var renderMode = RenderExecutor.RENDER_MODE_WHEN_DIRTY

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun getContext(type: String): IWebCanvasContextOnscreen {
        when(type) {
            "2d", "2D" -> contextOnscreen = WebCanvasContextOnscreen2D(this).also {
                it.renderExecutor.setLooper(looper)
                it.renderExecutor.setRenderMode(renderMode)
            }
            else -> TODO("getContext($type) Not support yet!!")
        }
        return contextOnscreen!!
    }

    override fun start(callback: () -> Looper?) {
        Log.d(TAG,  "start isStarted=$isStarted")
        if (isStarted) {
            return
        }
        myLooper = callback.invoke() ?: Looper.getMainLooper()
        contextOnscreen?.renderExecutor?.run {
            setRenderMode(renderMode)
            setLooper(looper)
            start()
        }
    }

    override fun stop(callback: (looper: Looper, isDefault: Boolean) -> Unit) {
        if (!isStarted) {
            return
        }
        val looper = myLooper!!
        if (contextOnscreen == null) {
            callback.invoke(looper, Looper.getMainLooper() == looper)
        } else {
            contextOnscreen?.renderExecutor?.stop {
                callback.invoke(looper, Looper.getMainLooper() == looper)
            }
        }
        myLooper = null
    }

    override fun setRenderMode(mode: Int) {
        if (renderMode == mode) {
            return
        }
        renderMode = mode
        contextOnscreen?.renderExecutor?.setRenderMode(mode)
    }

    override fun getRenderMode(): Int {
        return renderMode
    }

    override fun requestRender() {
        if (!isStarted) {
            return
        }
        contextOnscreen?.renderExecutor?.requestRender()
    }
}