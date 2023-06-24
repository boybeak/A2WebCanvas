package com.github.boybeak.webcanvas

import android.os.Handler

interface IWebCanvasContext {
    val handler: Handler
    fun onRenderModeChanged(mode: Int)
}