package com.github.boybeak.a2webcanvas.app.game

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.util.Log
import kotlin.random.Random

typealias OnPreparedListener = () -> Unit
typealias OnStartFinishListener = () -> Unit

open class GamePlayground(val name: String) {

    companion object {
        private const val TAG = "GamePlayground"
    }

    private val playgroundThread = object : HandlerThread("$name-playground") {
        override fun onLooperPrepared() {
            super.onLooperPrepared()
            onPrepared()
            onPreparedListener?.invoke()
            onPreparedListener = null
            onCreateFinish()
        }

        override fun run() {
            super.run()
            Log.d(TAG, "run STOPPED!!!")
        }
    }
    val playgroundLooper: Looper by lazy {
        initGameThreadIfNot()
        playgroundThread.looper
    }
    private val handler by lazy {
        Handler(playgroundLooper)
    }

    private var onPreparedListener: OnPreparedListener? = null
    private var onStartFinishListener: OnStartFinishListener? = null

    private fun initGameThreadIfNot() {
        if (playgroundThread.isAlive) {
            return
        }
        playgroundThread.start()
    }
    open fun onPrepared() {}
    fun onCreateFinish() {
        onStartFinishListener?.invoke()
        onStartFinishListener = null
    }
    fun setOnPreparedListener(listener: OnPreparedListener) {
        onPreparedListener = listener
    }
    open fun start(onStartFinish: OnStartFinishListener) {
        onStartFinishListener = onStartFinish
        initGameThreadIfNot()
    }
    open fun stop() {
        playgroundThread.quit()
    }
    fun gameRun(run: () -> Unit) {
        handler.post(run)
    }

}