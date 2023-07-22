package com.github.boybeak.v8canvas.context

import com.github.boybeak.canvas.context.WebCanvasContextOffscreen2D
import com.github.boybeak.v8canvas._2d.BaseWebCanvasContext2DV8
import com.github.boybeak.v8canvas.offscreen.V8WebCanvasOffscreen

class V8WebCanvasContextOffscreen2D(canvas: V8WebCanvasOffscreen) : BaseWebCanvasContext2DV8(canvas) {
    val bitmap get() = (context2D as WebCanvasContextOffscreen2D).bitmap
}