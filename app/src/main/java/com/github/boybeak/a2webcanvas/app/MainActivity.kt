package com.github.boybeak.a2webcanvas.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import com.github.boybeak.webcanvas.IWebCanvas
import com.github.boybeak.webcanvas.IWebCanvasContext
import com.github.boybeak.webcanvas.WebCanvasView
import com.github.boybeak.webcanvas.ext.post
import com.github.boybeak.webcanvas.twod.CanvasRenderingContext2D
import com.github.boybeak.webcanvas.twod.IWebCanvas2D

class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }

    private val canvasView by lazy { findViewById<WebCanvasView>(R.id.webCanvas) }
    private val invalidateButton by lazy { findViewById<Button>(R.id.invalidateButton) }
    private val houseButton by lazy { findViewById<Button>(R.id.houseButton) }
    private val handler = Handler(Looper.getMainLooper())

    private val task = Runnable {
        Log.d(TAG, "Run!!!!")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        canvasView.setRenderMode(IWebCanvas.RENDER_MODE_AUTO)
        houseButton.setOnClickListener {
            canvasView.getContext<CanvasRenderingContext2D>("2d").post {
                strokeRect(75F, 140F, 150F, 110F)
                fillRect(130F, 190F, 40F, 60F)

                beginPath()
                moveTo(50F, 140F)
                lineTo(150F, 60F)
                lineTo(250F, 140F)
                closePath()
                stroke()
            }
        }
    }

    private fun refresh() {
        handler.removeCallbacks(task)
        handler.post(task)
    }
}