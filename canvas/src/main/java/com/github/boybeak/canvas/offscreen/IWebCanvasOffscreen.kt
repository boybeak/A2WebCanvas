package com.github.boybeak.canvas.offscreen

import com.github.boybeak.canvas.IWebCanvas

interface IWebCanvasOffscreen : IWebCanvas {
    override fun getContext(type: String): IWebCanvasContextOffscreen
}