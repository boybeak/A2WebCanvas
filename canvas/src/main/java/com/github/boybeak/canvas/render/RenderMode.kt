package com.github.boybeak.canvas.render

interface RenderMode {
    companion object {
        const val RENDER_MODE_WHEN_DIRTY = 0
        const val RENDER_MODE_CONTINUOUSLY = 1
        const val RENDER_MODE_AUTO = 2
    }
}