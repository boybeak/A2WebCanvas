package com.github.boybeak.webcanvas.render

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.view.SurfaceHolder
import com.github.boybeak.webcanvas.IWebCanvas
import com.github.boybeak.webcanvas.IWebCanvasSurfaceHolder
import java.lang.IllegalArgumentException
import kotlin.math.ceil

internal abstract class AbsRenderer(internal val surfaceHolder: SurfaceHolder, context: Context) :
    IWebCanvasSurfaceHolder {

    companion object {
        private const val TAG = "AbsRenderer"
    }

    private val renderThread = HandlerThread("RendererThread").also {
        it.start()
    }
    val handler: Handler = Handler(renderThread.looper)

    private var renderMode: Int = -1
        private set

    val isContinuouslyRendering get() = renderMode == IWebCanvas.RENDER_MODE_CONTINUOUSLY
    val isAutoRendering get() = renderMode == IWebCanvas.RENDER_MODE_AUTO

    private val renderTask = Task()
    private val whenDirtyLogic = WhenDirtyLogic(handler, renderTask) { onRender() }
    private val continuouslyLogic = ContinuouslyLogic(context, handler, renderTask) { onRender() }
    private val autoLogic = AutoLogic(handler, renderTask) { onRender() }

    open fun onResume() {}
    open fun onPause() {}

    fun setRenderMode(mode: Int) {
        Log.d(TAG, "setRenderMode mode=$mode")
        if (renderMode == mode) {
            return
        }
        renderMode = mode
        renderTask.logic = when(renderMode) {
            IWebCanvas.RENDER_MODE_WHEN_DIRTY -> whenDirtyLogic
            IWebCanvas.RENDER_MODE_CONTINUOUSLY -> continuouslyLogic
            IWebCanvas.RENDER_MODE_AUTO -> autoLogic
            else -> throw IllegalArgumentException("Illegal renderMode $renderMode")
        }
        requestRender()
    }

    fun requestRender() {
        renderTask.logic?.requestRender()
    }

    fun isMyThread(): Boolean {
        return Thread.currentThread() == renderThread
    }

    fun executeOnMyThread(task: Runnable) {
        if (isMyThread()) {
            task.run()
        } else {
            handler.post(task)
        }
    }

    abstract fun onRender()

    private class Task : Runnable {
        var logic: RenderLogic? = null
            set(value) {
                if (field == value) {
                    return
                }
                field?.stopRendering()
                field = value
            }
        override fun run() {
            logic?.run()
        }
    }

    private interface RenderLogic {
        fun requestRender()
        fun stopRendering()
        fun run()
    }
    private abstract class AbsLogic(val handler: Handler, val task: Task, val onRender: () -> Unit) : RenderLogic {
        override fun run() {
            onRender()
        }
    }
    private class WhenDirtyLogic(handler: Handler, task: Task, onRender: () -> Unit) : AbsLogic(handler, task, onRender) {
        override fun requestRender() {
            handler.post(task)
        }

        override fun stopRendering() {
            handler.removeCallbacks(task)
        }
    }
    private open class AutoLogic(handler: Handler, task: Task, onRender: () -> Unit) : AbsLogic(handler, task, onRender) {
        var isPosted = false
        override fun requestRender() {
            if (isPosted) {
                handler.removeCallbacks(task)
                isPosted = false
            }
            handler.post(task)
            isPosted = true
        }

        override fun stopRendering() {
            if (isPosted) {
                handler.removeCallbacks(task)
                isPosted = false
            }
        }

        override fun run() {
            isPosted = false
            super.run()
        }
    }
    private class ContinuouslyLogic(context: Context, handler: Handler, task: Task, onRender: () -> Unit) : AutoLogic(handler, task, onRender) {
        private val period = 1000L / ceil((context as Activity).windowManager.defaultDisplay.refreshRate).toInt()
        private var isRendering = false
        override fun requestRender() {
            if (isRendering) {
                return
            }
            super.requestRender()
            isRendering = true
        }
        override fun run() {
            if (!isRendering) {
                return
            }
            super.run()
            handler.postDelayed(task, period)
            isPosted = true
        }

        override fun stopRendering() {
            super.stopRendering()
            isRendering = false
        }
    }

}