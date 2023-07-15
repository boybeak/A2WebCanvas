package com.github.boybeak.canvas.onscreen

import com.github.boybeak.canvas.IWebCanvasContext
import com.github.boybeak.canvas.render.RenderExecutor

interface IWebCanvasContextOnscreen : IWebCanvasContext {
    override val canvas: IWebCanvasOnscreen
    val renderExecutor: RenderExecutor
    fun startRender()
    fun stopRender()
    fun post(task: Runnable) {
        renderExecutor.post(task)
    }
    fun postDelayed(delay: Long, task: Runnable) {
        renderExecutor.postDelayed(delay, task)
    }
    fun postInvalidate() {
        renderExecutor.requestRender()
    }
    fun remove(task: Runnable) {
        renderExecutor.remove(task)
    }
}