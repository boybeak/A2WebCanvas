package com.github.boybeak.webcanvas.render

import android.content.Context
import com.github.boybeak.webcanvas.twod.CanvasProvider
import com.github.boybeak.webcanvas.twod.IWebCanvas2D

internal abstract class AbsRenderer2D(iWebCanvas2D: IWebCanvas2D) : AbsRenderer(iWebCanvas2D), CanvasProvider {
}