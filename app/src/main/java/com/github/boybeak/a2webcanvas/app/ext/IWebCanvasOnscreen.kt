package com.github.boybeak.a2webcanvas.app.ext

import com.github.boybeak.canvas._2d.IWebCanvasContext2D
import com.github.boybeak.canvas.onscreen.IWebCanvasOnscreen

fun IWebCanvasOnscreen.context2DPost(callback: IWebCanvasContext2D.() -> Unit) {
    val context2D = getContext("2d") as IWebCanvasContext2D
    queueEvent {
        callback.invoke(context2D)
    }
}