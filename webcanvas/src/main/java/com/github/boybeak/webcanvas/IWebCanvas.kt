package com.github.boybeak.webcanvas

import android.content.Context
import android.os.Looper
import android.view.SurfaceHolder

interface IWebCanvas {
    companion object {
        const val RENDER_MODE_WHEN_DIRTY = 0
        const val RENDER_MODE_CONTINUOUSLY = 1
        const val RENDER_MODE_AUTO = 2
    }

    val surfaceHolder: SurfaceHolder
    fun <T : IWebCanvasContext> getContext(type: String): T
    fun getPlatformContext(): Context
    fun queueEvent(event: Runnable)
    fun queueEvent(delayInMills: Long, event: Runnable)
    fun removeEvent(event: Runnable)
}