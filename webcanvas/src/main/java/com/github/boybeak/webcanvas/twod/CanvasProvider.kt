package com.github.boybeak.webcanvas.twod

import android.graphics.Canvas

interface CanvasProvider {

    val canvas: Canvas

    fun setCallback(callback: Callback?)

    interface Callback {
        fun onCanvasCreated(canvas: Canvas)
        fun onCanvasCommit(canvas: Canvas)
        fun onCanvasDestroyed()
    }
}