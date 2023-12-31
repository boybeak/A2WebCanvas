package com.github.boybeak.webcanvas.image

import android.graphics.Bitmap
import android.util.Log

typealias OnLoad = () -> Unit
typealias OnError = (e: Throwable) -> Unit

class HTMLImageElement internal constructor(private val decoder: ISrcDecoder): AbsWebImage() {

    companion object {
        private const val TAG = "HTMLImageElement"
    }

    var src: String? = null
        set(value) {
            field = value
            decoder.decode(value, object : ISrcDecoder.Callback {
                override fun onLoad(bitmap: Bitmap) {
                    this@HTMLImageElement.bitmap = bitmap
                    onLoad?.invoke()
                }

                override fun onError(e: Throwable) {
                    onError?.invoke(e)
                }
            })
        }

    override var bitmap: Bitmap? = null
        private set

    private var onLoad: OnLoad? = null
    private var onError: OnError? =  null

    fun setOnLoad(onLoad: OnLoad) {
        if (bitmap != null) {
            onLoad.invoke()
        }
        this.onLoad = onLoad
    }
    fun setOnError(onError: OnError) {
        this.onError = onError
    }
}