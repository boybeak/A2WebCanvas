package com.github.boybeak.canvas.render

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import java.lang.IllegalArgumentException
import kotlin.math.ceil
typealias StopCallback = () -> Unit

class RenderExecutor(context: Context, private val callback: Callback) {

    companion object {
        private const val TAG = "RenderExecutor"
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

    private var renderMode = RenderMode.RENDER_MODE_WHEN_DIRTY

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
    fun stop(callback: StopCallback) {
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
            RenderMode.RENDER_MODE_WHEN_DIRTY -> whenDirtyStrategy
            RenderMode.RENDER_MODE_CONTINUOUSLY -> continuouslyStrategy
            RenderMode.RENDER_MODE_AUTO -> autoStrategy
            else -> throw IllegalArgumentException("setRenderMode unsupported mode=$mode")
        }
        renderMode = mode
        renderStrategy.requestRender()
    }
    fun post(task: Runnable): Boolean {
        if (handler == null) {
            return false
        }
        handler?.post(task)
        return true
    }
    fun postDelayed(delay: Long, task: Runnable): Boolean {
        if (handler == null) {
            return false
        }
        handler?.postDelayed(task, delay)
        return true
    }
    fun remove(task: Runnable): Boolean {
        if (handler == null) {
            return false
        }
        handler?.removeCallbacks(task)
        return true
    }

    interface Callback {
        fun onRender()
        fun onStop()
    }
}