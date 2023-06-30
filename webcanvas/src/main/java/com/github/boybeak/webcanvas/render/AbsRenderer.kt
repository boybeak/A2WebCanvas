package com.github.boybeak.webcanvas.render

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.util.Log
import com.github.boybeak.webcanvas.IWebCanvas
import com.github.boybeak.webcanvas.IWebCanvasSurfaceHolder
import java.lang.IllegalArgumentException
import kotlin.math.ceil

internal abstract class AbsRenderer(private val iWebCanvas: IWebCanvas) :
    IWebCanvasSurfaceHolder {

    companion object {
        private const val TAG = "AbsRenderer"
    }

    private val context: Context get() = iWebCanvas.getPlatformContext()

    internal val poster = object : EventPoster {
        override fun postEvent(event: Runnable) {
            iWebCanvas.queueEvent(event)
        }

        override fun postEventDelayed(delayInMills: Long, event: Runnable) {
            iWebCanvas.queueEvent(delayInMills, event)
        }

        override fun removeEvent(event: Runnable) {
            iWebCanvas.removeEvent(event)
        }
    }

    private var renderMode: Int = -1

    val isContinuouslyRendering get() = renderMode == IWebCanvas.RENDER_MODE_CONTINUOUSLY
    val isAutoRendering get() = renderMode == IWebCanvas.RENDER_MODE_AUTO

    private val renderTask = Task()
    private val whenDirtyLogic = WhenDirtyLogic(poster, renderTask) { onRender() }
    private val continuouslyLogic = ContinuouslyLogic(context, poster, renderTask) { onRender() }
    private val autoLogic = AutoLogic(poster, renderTask) { onRender() }

    open fun onResume() {}
    open fun onPause() {}

    fun setRenderMode(mode: Int) {
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
    private abstract class AbsLogic(val poster: EventPoster, val task: Task, val onRender: () -> Unit) : RenderLogic {
        override fun run() {
            onRender()
        }
    }
    private class WhenDirtyLogic(poster: EventPoster, task: Task, onRender: () -> Unit) : AbsLogic(poster, task, onRender) {
        override fun requestRender() {
            poster.postEvent(task)
        }

        override fun stopRendering() {
            poster.removeEvent(task)
        }
    }
    private open class AutoLogic(handler: EventPoster, task: Task, onRender: () -> Unit) : AbsLogic(handler, task, onRender) {
        var isPosted = false
        override fun requestRender() {
            if (isPosted) {
                return
            }
            poster.postEvent(task)
            isPosted = true
        }

        override fun stopRendering() {
            if (isPosted) {
                poster.removeEvent(task)
                isPosted = false
            }
        }

        override fun run() {
            isPosted = false
            super.run()
        }
    }
    private class ContinuouslyLogic(context: Context, poster: EventPoster, task: Task, onRender: () -> Unit) : AutoLogic(poster, task, onRender) {
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
            poster.postEventDelayed(period, task)
            isPosted = true
        }

        override fun stopRendering() {
            super.stopRendering()
            isRendering = false
        }
    }

}