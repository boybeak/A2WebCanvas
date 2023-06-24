package com.github.boybeak.webcanvas.ext

import com.github.boybeak.webcanvas.IWebCanvasContext

fun <T : IWebCanvasContext> T.post(task: T.() -> Unit) {
    this.handler.post {
        task.invoke(this)
    }
}