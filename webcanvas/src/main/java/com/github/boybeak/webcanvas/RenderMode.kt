package com.github.boybeak.webcanvas

import androidx.annotation.IntDef
import com.github.boybeak.webcanvas.IWebCanvas.Companion.RENDER_MODE_AUTO
import com.github.boybeak.webcanvas.IWebCanvas.Companion.RENDER_MODE_CONTINUOUSLY
import com.github.boybeak.webcanvas.IWebCanvas.Companion.RENDER_MODE_WHEN_DIRTY

@IntDef(RENDER_MODE_WHEN_DIRTY, RENDER_MODE_CONTINUOUSLY, RENDER_MODE_AUTO)
annotation class RenderMode