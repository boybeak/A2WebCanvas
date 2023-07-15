package com.github.boybeak.canvas.render

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import java.lang.IllegalArgumentException
import kotlin.math.ceil

class RenderExecutor(context: Context, private val callback: Callback) {

    companion object {
        const val RENDER_MODE_WHEN_DIRTY = 0
        const val RENDER_MODE_CONTINUOUSLY = 1
        const val RENDER_MODE_AUTO = 2
    }

    private var stopCallback: (() -> Unit)? = null
    private val stopTask = Runnable {
        callback.onStop()
        stopCallback?.invoke()
        stopCallback = null
    }

    private var handler: Handler? = null
    val isStarted get() = handler != null

    private val fps = (context as Activity).windowManager.defaultDisplay.refreshRate

    private val autoStrategy by lazy { AutoStrategy(this) }
    private val continuouslyStrategy by lazy { ContinuouslyStrategy(this, ceil(fps).toInt()) }
    private val whenDirtyStrategy = WhenDirtyStrategy(this)
    private var renderStrategy: RenderStrategy = whenDirtyStrategy

    private var renderMode = RENDER_MODE_WHEN_DIRTY

    fun setLooper(looper: Looper) {
        if (isStarted) {
            throw IllegalStateException("Must call stop before setLooper")
        }
        handler = Handler(looper)
    }

    fun onRender() {
        callback.onRender()
    }

    fun start() {
        renderStrategy.requestRender()
    }
    fun stop(callback: () -> Unit) {
        this.stopCallback = callback
        renderStrategy.stop()
        handler?.post(stopTask)
        handler = null
    }
    fun requestRender() {
        renderStrategy.requestRender()
    }
    fun setRenderMode(mode: Int) {
        if (mode == renderMode) {
            return
        }
        renderStrategy.stop()
        renderStrategy = when(mode) {
            RENDER_MODE_WHEN_DIRTY -> whenDirtyStrategy
            RENDER_MODE_CONTINUOUSLY -> continuouslyStrategy
            RENDER_MODE_AUTO -> autoStrategy
            else -> throw IllegalArgumentException("setRenderMode unsupported mode=$mode")
        }
        renderStrategy.stop()
    }
    fun post(task: Runnable) {
        handler?.post(task)
    }
    fun postDelayed(delay: Long, task: Runnable) {
        handler?.postDelayed(task, delay)
    }
    fun remove(task: Runnable) {
        handler?.removeCallbacks(task)
    }

    interface Callback {
        fun onRender()
        fun onStop()
    }
}