package com.github.boybeak.a2webcanvas.app.experiment

import android.content.DialogInterface
import android.content.DialogInterface.OnClickListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.HandlerThread
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.github.boybeak.a2webcanvas.app.R
import com.github.boybeak.a2webcanvas.app.widget.InfoCard
import com.github.boybeak.canvas._2d.paint.Style
import com.github.boybeak.canvas.context.WebCanvasContextOnscreen2D
import com.github.boybeak.canvas.onscreen.WebCanvasOnscreen
import com.github.boybeak.canvas.render.RenderExecutor
import com.github.boybeak.canvas.render.RenderMode

class OnScreenActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "OnScreenActivity"
    }

    private val renderModeNames = arrayOf(
        "WHEN_DIRTY", "CONTINUOUSLY", "AUTO"
    )
    private val renderModes = arrayOf(
        RenderMode.RENDER_MODE_WHEN_DIRTY,
        RenderMode.RENDER_MODE_CONTINUOUSLY,
        RenderMode.RENDER_MODE_AUTO
    )

    private val webCanvas: WebCanvasOnscreen by lazy { findViewById(R.id.webCanvas) }
    private val renderModeBtn: InfoCard by lazy { findViewById(R.id.renderMode) }

    private val renderThreadA = HandlerThread("RenderA").also { it.start() }
    private val renderThreadB = HandlerThread("RenderB").also { it.start() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_screen)

        renderModeBtn.setText(renderModeNames[renderModes.indexOf(webCanvas.getRenderMode())])

        findViewById<InfoCard>(R.id.getContext).setOnClickListener {
            webCanvas.start {
                toast("Start with A")
                renderThreadA.looper
            }
            webCanvas.getContextAs<WebCanvasContextOnscreen2D>("2d").draw {
                fillStyle = Style.ColorStyle("white")
                fillRect(0F, 0F, webCanvas.width / 2F, webCanvas.height / 2F)
            }
        }
        renderModeBtn.setOnClickListener {
            AlertDialog.Builder(this)
                .setSingleChoiceItems(renderModeNames, renderModes.indexOf(webCanvas.getRenderMode())) { dialog, which ->
                    webCanvas.setRenderMode(renderModes[which])
                    renderModeBtn.setText(renderModeNames[which])
                    dialog?.dismiss()
                }
                .show()
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
        findViewById<InfoCard>(R.id.requestRenderManual).setOnClickListener {
            webCanvas.requestRender()
        }
    }

    private fun toast(text: String) {
        runOnUiThread {
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
        }
    }
}