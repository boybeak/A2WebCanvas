package com.github.boybeak.a2webcanvas.app.experiment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.HandlerThread
import android.util.Log
import android.widget.Toast
import com.github.boybeak.a2webcanvas.app.R
import com.github.boybeak.a2webcanvas.app.widget.InfoCard
import com.github.boybeak.canvas.WebCanvasOnscreen

class OnScreenActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "OnScreenActivity"
    }

    private val webCanvas: WebCanvasOnscreen by lazy { findViewById(R.id.webCanvas) }

    private val renderThreadA = HandlerThread("RenderA").also { it.start() }
    private val renderThreadB = HandlerThread("RenderB").also { it.start() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_screen)

        findViewById<InfoCard>(R.id.getContext).setOnClickListener {
            webCanvas.start {
                toast("Start with A")
                renderThreadA.looper
            }
            val canvasContext = webCanvas.getContext("2d")
        }
        findViewById<InfoCard>(R.id.changeLooper).setOnClickListener {
            if (webCanvas.isStarted) {
                if (webCanvas.looper == renderThreadA.looper) {
                    Log.d(TAG, "is A")
                    webCanvas.stop { looper, isDefault ->
                        Log.d(TAG, "STOP 1")
                        webCanvas.start {
                            Log.d(TAG, "START 1")
                            toast("Switch to B")
                            renderThreadB.looper
                        }
                    }
                } else {
                    Log.d(TAG, "is B")
                    webCanvas.stop { looper, isDefault ->
                        Log.d(TAG, "STOP 2")
                        webCanvas.start {
                            Log.d(TAG, "START 2")
                            toast("Switch to A")
                            renderThreadA.looper
                        }
                    }
                }

            } else {
                webCanvas.start {
                    toast("Start with A")
                    renderThreadA.looper
                }
            }
        }
    }

    private fun toast(text: String) {
        runOnUiThread {
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
        }
    }
}