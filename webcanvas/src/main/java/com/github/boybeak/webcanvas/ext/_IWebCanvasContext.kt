package com.github.boybeak.webcanvas.ext

import com.github.boybeak.webcanvas.IWebCanvasContext
import com.github.boybeak.webcanvas.WebCanvasView
import com.github.boybeak.webcanvas.twod.CanvasRenderingContext2D
import com.github.boybeak.webcanvas.twod.ICanvasRenderingContext2D

fun <T : IWebCanvasContext> T.post(task: T.() -> Unit) {
    this.handler.post {
        task.invoke(this)
    }
}
fun WebCanvasView.context2DPost(callback : ICanvasRenderingContext2D.() -> Unit) {
    getContext<CanvasRenderingContext2D>("2d").post(callback)
}