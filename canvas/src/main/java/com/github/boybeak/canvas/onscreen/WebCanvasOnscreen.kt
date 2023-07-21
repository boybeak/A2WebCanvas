package com.github.boybeak.canvas.onscreen

import android.content.Context
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.github.boybeak.canvas.context.WebCanvasContextOnscreen2D
import com.github.boybeak.canvas.render.RenderExecutor
import com.github.boybeak.canvas.render.RenderMode

open class WebCanvasOnscreen : SurfaceView, IWebCanvasOnscreen {

    companion object {
        private const val TAG = "WebCanvasOnscreen"
    }

    override var canvasWidth: Int
        get() = TODO("Not yet implemented")
        set(value) {}
    override var canvasHeight: Int
        get() = TODO("Not yet implemented")
        set(value) {}

    private var contextOnscreen: AbsWebCanvasContextOnscreen? = null

    override val surfaceHolder: SurfaceHolder
        get() = holder

    private var myLooper: Looper? = null
    override val looper: Looper get() = myLooper!!

    override val androidContext: Context
        get() = context
    override val isStarted: Boolean
        get() = myLooper != null

    private var renderMode = RenderMode.RENDER_MODE_WHEN_DIRTY

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    fun <T : IWebCanvasContextOnscreen> getContextAs(type: String) : T {
        return getContext(type) as T
    }
    override fun getContext(type: String): IWebCanvasContextOnscreen {
        Log.d(TAG, "getContext-1 type=$type")
        if (contextOnscreen == null) {
            when(type) {
                "2d", "2D" -> contextOnscreen = WebCanvasContextOnscreen2D(this).also {
                    it.setLooper(looper)
                    it.setRenderMode(renderMode)
                }
                else -> TODO("getContext($type) Not support yet!!")
            }
        }
        Log.d(TAG, "getContext-2 type=$type")
        return contextOnscreen!!
    }

    override fun start(callback: () -> Looper?) {
        Log.d(TAG,  "start isStarted=$isStarted")
        if (isStarted) {
            return
        }
        myLooper = callback.invoke() ?: Looper.getMainLooper()
        contextOnscreen?.run {
            setRenderMode(renderMode)
            setLooper(looper)
            startRender()
        }
    }

    override fun stop(callback: (looper: Looper, isDefault: Boolean) -> Unit) {
        Log.d(TAG, "stop isStarted=$isStarted contextOnscreen=$contextOnscreen")
        if (!isStarted) {
            return
        }
        val looper = myLooper!!
        if (contextOnscreen == null) {
            callback.invoke(looper, Looper.getMainLooper() == looper)
        } else {
            contextOnscreen?.stopRender {
                Log.d(TAG,  "stop result")
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
        contextOnscreen?.setRenderMode(mode)
    }

    override fun getRenderMode(): Int {
        return renderMode
    }

    override fun requestRender() {
        Log.d(TAG,  "requestRender in canvas")
        if (!isStarted) {
            return
        }
        contextOnscreen?.requestRender()
    }

    override fun queueEvent(task: Runnable): Boolean {
        return contextOnscreen?.post(task) ?: false
    }

    override fun queueEvent(delay: Long, task: Runnable): Boolean {
        Log.d(TAG, "queueEvent contextOnscreen=$contextOnscreen")
        return contextOnscreen?.postDelayed(delay, task) ?: false
    }
}