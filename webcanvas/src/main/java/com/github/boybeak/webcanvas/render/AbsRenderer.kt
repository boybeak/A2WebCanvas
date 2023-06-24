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

    fun setRenderMode(mode: Int) {
        if (renderMode == mode) {
            return
        }
        renderMode = mode
        requestRender()
    }

    fun requestRender() {
        renderTask.logic = when(renderMode) {
            IWebCanvas.RENDER_MODE_WHEN_DIRTY -> whenDirtyLogic
            IWebCanvas.RENDER_MODE_CONTINUOUSLY -> continuouslyLogic
            IWebCanvas.RENDER_MODE_AUTO -> autoLogic
            else -> throw IllegalArgumentException("Illegal renderMode $renderMode")
        }
        renderTask.logic?.requestRender()
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
                Log.d(TAG, "Task.logic.set done value=$value")
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
            Log.d(TAG, "AutoLogic.startRendering")
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
        override fun run() {
            super.run()
            handler.postDelayed(task, period)
            isPosted = true
        }
    }

}