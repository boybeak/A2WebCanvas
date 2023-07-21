package com.github.boybeak.canvas

interface IWebCanvas {

    companion object {
        const val SIZE_WIDTH_DEFAULT = 300
        const val SIZE_HEIGHT_DEFAULT = 300
    }

    var canvasWidth: Int
    var canvasHeight: Int
    fun getContext(type: String): IWebCanvasContext
}