package com.github.boybeak.canvas.offscreen

import com.github.boybeak.canvas.IWebCanvas
import com.github.boybeak.canvas.context.WebCanvasContextOffscreen2D
import java.lang.IllegalArgumentException

open class WebCanvasOffscreen : IWebCanvasOffscreen {

    override var canvasWidth: Int = IWebCanvas.SIZE_WIDTH_DEFAULT
    override var canvasHeight: Int = IWebCanvas.SIZE_HEIGHT_DEFAULT

    private var contextOffscreen: IWebCanvasContextOffscreen? = null

    override fun getContext(type: String): IWebCanvasContextOffscreen {
        if (contextOffscreen == null) {
            contextOffscreen = when(type) {
                "2d" -> WebCanvasContextOffscreen2D(this)
                "webgl" -> TODO("")
                else -> throw IllegalArgumentException("Unsupported type $type")
            }
        }
        return contextOffscreen!!
    }

}