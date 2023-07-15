package com.github.boybeak.canvas.onscreen

import com.github.boybeak.canvas.IWebCanvasContext
import com.github.boybeak.canvas.render.RenderExecutor

interface IWebCanvasContextOnscreen : IWebCanvasContext {
    override val canvas: IWebCanvasOnscreen
    fun post(task: Runnable)
    fun postDelayed(delay: Long, task: Runnable)
    fun remove(task: Runnable)
}