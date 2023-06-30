package com.github.boybeak.a2webcanvas.app.game

import android.os.Handler
import android.os.HandlerThread

object GameExecutor {
    private val gameThread = HandlerThread("GameThread")
    private var gameRunnable: Runnable? = null
    private val gameTask = object : Runnable {
        override fun run() {
            val gameRun = gameRunnable ?: return
            gameRun.run()
            gameHandler.postDelayed(this, 16L)
        }
    }
    private val gameHandler: Handler
    init {
        gameThread.start()
        gameHandler = Handler(gameThread.looper)
    }
    fun start(initTask: Runnable, loopTask: Runnable) {
        gameHandler.post(initTask)
        gameRunnable = loopTask
        gameHandler.post(gameTask)
    }
    fun pause() {

    }
    fun resume() {

    }
    fun stop() {
        gameRunnable = null
    }
}