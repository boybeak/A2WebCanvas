package com.github.boybeak.a2webcanvas.app.debug

import android.content.Context
import android.graphics.SurfaceTexture
import android.opengl.EGLSurface
import android.util.AttributeSet
import android.view.Surface
import android.view.SurfaceControl
import android.view.TextureView

class WebCanvasTextureView : TextureView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )
    init {
    }
}