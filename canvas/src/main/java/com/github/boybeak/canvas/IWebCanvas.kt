package com.github.boybeak.canvas

interface IWebCanvas {
    fun getContext(type: String): IWebCanvasContext
}