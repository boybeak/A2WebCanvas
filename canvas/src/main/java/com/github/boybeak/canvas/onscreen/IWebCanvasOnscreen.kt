package com.github.boybeak.canvas.onscreen

import android.content.Context
import android.os.Looper
import android.view.SurfaceHolder
import com.github.boybeak.canvas.IWebCanvas

interface IWebCanvasOnscreen : IWebCanvas {
    val surfaceHolder: SurfaceHolder
    val looper: Looper
    val androidContext: Context
    val isStarted: Boolean

    override fun getContext(type: String): IWebCanvasContextOnscreen
    fun start(callback: () -> Looper?)
    fun stop(callback: (looper: Looper, isDefault: Boolean) -> Unit)
    fun setRenderMode(mode: Int)
    fun getRenderMode(): Int
    fun requestRender()
}