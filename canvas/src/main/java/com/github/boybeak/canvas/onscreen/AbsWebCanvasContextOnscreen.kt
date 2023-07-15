package com.github.boybeak.canvas.onscreen

import android.os.Looper
import android.util.Log
import com.github.boybeak.canvas.context.WebCanvasContextOnscreen2D
import com.github.boybeak.canvas.render.RenderExecutor
import com.github.boybeak.canvas.render.StopCallback

abstract class AbsWebCanvasContextOnscreen(final override val canvas: IWebCanvasOnscreen) : IWebCanvasContextOnscreen {
    companion object {
        private const val TAG = "AbsWebCanvasContextOnscreen"
    }
    private val renderCallback = object : RenderExecutor.Callback {
        override fun onRender() {
            onFrameRender()
        }

        override fun onStop() {
            onStopRender()
        }
    }
    private val renderExecutor: RenderExecutor = RenderExecutor(canvas.androidContext, renderCallback)

    internal abstract fun onFrameRender()
    internal abstract fun onStopRender()

    internal fun setLooper(looper: Looper) {
        renderExecutor.setLooper(looper)
    }

    internal fun setRenderMode(mode: Int) {
        renderExecutor.setRenderMode(mode)
    }

    internal fun startRender() {
        renderExecutor.start()
    }

    internal fun requestRender() {
        Log.d(TAG, "requestRender")
        renderExecutor.requestRender()
    }

    internal fun stopRender(callback: StopCallback) {
        renderExecutor.stop(callback)
    }

    override fun post(task: Runnable) {
        renderExecutor.post(task)
    }

    override fun postDelayed(delay: Long, task: Runnable) {
        renderExecutor.postDelayed(delay, task)
    }

    override fun remove(task: Runnable) {
        renderExecutor.remove(task)
    }
}