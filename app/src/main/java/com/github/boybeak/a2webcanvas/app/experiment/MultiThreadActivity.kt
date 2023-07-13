package com.github.boybeak.a2webcanvas.app.experiment

import android.graphics.Canvas
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.Button
import android.widget.Toast
import com.github.boybeak.a2webcanvas.app.R

class MultiThreadActivity : AppCompatActivity() {

    private val playground: SurfaceView by lazy { findViewById(R.id.playground) }
    private val mainBtn: Button by lazy { findViewById(R.id.threadMain) }
    private val aBtn: Button by lazy { findViewById(R.id.threadA) }
    private val bBtn: Button by lazy { findViewById(R.id.threadB) }
    private val xBtn: Button by lazy { findViewById(R.id.threadX) }

    private val threadA = HandlerThread("A").also {
        it.start()
    }
    private val handlerA = Handler(threadA.looper)
    private val threadB = HandlerThread("B").also {
        it.start()
    }
    private val handlerB = Handler(threadB.looper)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multi_thread)

        playground.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                Toast.makeText(this@MultiThreadActivity, "surfaceCreated", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
            }
        })

        mainBtn.setOnClickListener {
            runOnUiThread {
                threadDraw {
                    drawColor(Color.YELLOW)
                }
            }
        }
        aBtn.setOnClickListener {
            handlerA.post {
                threadDraw {
                    drawColor(Color.RED)
                }
            }
        }
        bBtn.setOnClickListener {
            handlerB.post {
                threadDraw {
                    drawColor(Color.GREEN)
                }
            }
        }
        xBtn.setOnClickListener {
            xDraw()
        }

    }

    private fun threadDraw(draw: Canvas.() -> Unit) {
        val canvas = playground.holder.lockCanvas()
        draw.invoke(canvas)
        playground.holder.unlockCanvasAndPost(canvas)
    }
    private fun xDraw() {
        handlerA.post {
            val canvas = playground.holder.lockCanvas()
            runOnUiThread {
                canvas.drawColor(Color.BLUE)
                handlerB.post {
                    playground.holder.unlockCanvasAndPost(canvas)
                }
            }
        }
    }

}