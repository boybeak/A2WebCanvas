package com.github.boybeak.webcanvas

import android.os.Handler

interface IWebCanvasContext {

    fun onResume()
    fun onPause()
    fun onRenderModeChanged(mode: Int)
    fun requestRender()

}