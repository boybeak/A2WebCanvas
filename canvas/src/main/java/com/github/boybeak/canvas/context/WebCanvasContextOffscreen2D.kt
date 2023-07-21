package com.github.boybeak.canvas.context

import android.graphics.Bitmap
import android.graphics.Canvas
import com.github.boybeak.canvas._2d.IWebCanvasContext2D
import com.github.boybeak.canvas.offscreen.IWebCanvasContextOffscreen
import com.github.boybeak.canvas.offscreen.IWebCanvasOffscreen
import com.github.boybeak.canvas.painter.IPainter2D
import com.github.boybeak.canvas.painter.Painter2D

class WebCanvasContextOffscreen2D(override val canvas: IWebCanvasOffscreen) : IWebCanvasContextOffscreen, IWebCanvasContext2D {
    override val canvasPainter: IPainter2D = Painter2D(this)
    override val androidCanvas: Canvas get() = canvasInner

    var bitmap = Bitmap.createBitmap(canvas.canvasWidth, canvas.canvasHeight, Bitmap.Config.ARGB_8888)
        private set
    private var canvasInner: Canvas = Canvas(bitmap)
}