package com.github.boybeak.canvas.onscreen

import com.github.boybeak.canvas.IWebCanvasContext
import com.github.boybeak.canvas.render.RenderExecutor

interface IWebCanvasContextOnscreen : IWebCanvasContext {
    override val canvas: IWebCanvasOnscreen
    fun post(task: Runnable): Boolean
    fun postDelayed(delay: Long, task: Runnable): Boolean
    fun remove(task: Runnable): Boolean
}