package com.github.boybeak.canvas._2d

import com.github.boybeak.canvas.IWebCanvasContext
import com.github.boybeak.canvas.painter.IAndroidCanvasProvider
import com.github.boybeak.canvas.painter.IPainter2D

interface IWebCanvasContext2D : IWebCanvasContext, IPainter2D, IAndroidCanvasProvider {
}