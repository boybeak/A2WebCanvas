package com.github.boybeak.webcanvas

import android.os.Handler

interface IWebCanvasContext {
    val handler: Handler

    fun onResume()
    fun onPause()
    fun onRenderModeChanged(mode: Int)
    fun requestRender()
}