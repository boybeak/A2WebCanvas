package com.github.boybeak.webcanvas.ext

import com.github.boybeak.webcanvas.WebCanvasView
import com.github.boybeak.webcanvas.twod.CanvasRenderingContext2D
import com.github.boybeak.webcanvas.twod.ICanvasRenderingContext2D

fun WebCanvasView.context2DPost(callback : ICanvasRenderingContext2D.() -> Unit) {
    queueEvent {
        callback.invoke(getContext<CanvasRenderingContext2D>("2d"))
    }
}