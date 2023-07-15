package com.github.boybeak.canvas.render

import android.util.Log

abstract class RenderStrategy(private val executor: RenderExecutor) {

    companion object {
        private const val TAG = "RenderStrategy"
    }

    abstract val renderTask: Runnable
    open fun requestRender() {
        executor.post(renderTask)
    }
    open fun stop() {
        executor.remove(renderTask)
    }
    open fun onRender() {
        executor.onRender()
    }
}
class WhenDirtyStrategy(executor: RenderExecutor) : RenderStrategy(executor) {
    override val renderTask: Runnable = Runnable { onRender() }
}
class ContinuouslyStrategy(executor: RenderExecutor, fps: Int) : RenderStrategy(executor) {
    private val period = 1000L / fps
    override val renderTask: Runnable = object : Runnable {
        override fun run() {
            if (!isRendering) {
                return
            }
            onRender()
            executor.postDelayed(period, this)
        }
    }

    private var isRendering = false

    override fun requestRender() {
        if (isRendering) {
            return
        }
        isRendering = true
        super.requestRender()
    }

    override fun stop() {
        isRendering = false
        super.stop()
    }
}
class AutoStrategy(executor: RenderExecutor) : RenderStrategy(executor) {
    override val renderTask: Runnable = Runnable {
        onRender()
        isPosted = false
    }
    private var isPosted = false
    override fun requestRender() {
        if (isPosted) {
            return
        }
        super.requestRender()
        isPosted = true
    }

    override fun stop() {
        isPosted = false
        super.stop()
    }

    override fun onRender() {
        super.onRender()
        isPosted = false
    }
}
