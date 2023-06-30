package com.github.boybeak.a2webcanvas.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.github.boybeak.a2webcanvas.app.adapter.ApiItem
import com.github.boybeak.a2webcanvas.app.game.StickGame
import com.github.boybeak.adapter.AnyAdapter
import com.github.boybeak.adapter.event.OnItemClick
import com.github.boybeak.webcanvas.IWebCanvas
import com.github.boybeak.webcanvas.WebCanvasView
import com.github.boybeak.webcanvas.ext.context2DPost
import com.github.boybeak.webcanvas.twod.CanvasRenderingContext2D
import com.google.android.material.bottomappbar.BottomAppBar

class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }

    private val runningApi by lazy { findViewById<TextView>(R.id.runningApi) }
    private val bottomAppBar by lazy { findViewById<BottomAppBar>(R.id.bottomAppBar) }
    private val canvasView by lazy { findViewById<WebCanvasView>(R.id.webCanvas) }
    private val apisRecyclerView by lazy { findViewById<RecyclerView>(R.id.apisRv) }

    private var lastDraw: (CanvasRenderingContext2D.() -> Unit)? = null

    private val gifTask by lazy { StickGame(canvasView) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomAppBar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.v8Item -> {
                    startActivity(Intent(this, V8Activity::class.java))
                }
                R.id.debugItem -> {
                    startActivity(Intent(this, DebugActivity::class.java))
                }
                R.id.resetItem -> {
                    canvasView.context2DPost {
                        reset()
                    }
                }
                R.id.animationItem -> {
                    if (gifTask.isRunning) {
                        gifTask.finish()
                    } else {
                        runningApi.text = getString(R.string.text_running_api, "Animation")
                        gifTask.start()
                    }
                }
                R.id.invalidateItem -> {
                    canvasView.requestRender()
                }
                R.id.whenDirtyItem -> {
                    it.isChecked = true
                    canvasView.setRenderMode(IWebCanvas.RENDER_MODE_WHEN_DIRTY)
                    Toast.makeText(this@MainActivity, it.title, Toast.LENGTH_SHORT).show()
                }
                R.id.continuouslyItem -> {
                    it.isChecked = true
                    canvasView.setRenderMode(IWebCanvas.RENDER_MODE_CONTINUOUSLY)
                    Toast.makeText(this@MainActivity, it.title, Toast.LENGTH_SHORT).show()

                }
                R.id.autoItem -> {
                    it.isChecked = true
                    canvasView.setRenderMode(IWebCanvas.RENDER_MODE_AUTO)
                    Toast.makeText(this@MainActivity, it.title, Toast.LENGTH_SHORT).show()
                }
            }
            true
        }

        bottomAppBar.menu.performIdentifierAction(R.id.autoItem, 0)

        val apis2D = CanvasApis2D(canvasView)
        apisRecyclerView.adapter = AnyAdapter().apply {
            addAll(apis2D.apis) { s, _ ->
                ApiItem(s)
            }
            setOnClickFor(ApiItem::class.java, object : OnItemClick<ApiItem>() {
                override fun onClick(
                    view: View,
                    item: ApiItem,
                    position: Int,
                    adapter: AnyAdapter
                ) {
                    runningApi.text = getString(R.string.text_running_api, item.source().name)
                    canvasView.context2DPost(item.source().onDraw)
                    lastDraw = item.source().onDraw
                }
            })
        }

    }

    override fun onResume() {
        super.onResume()
        canvasView.onResume()
        if (gifTask.isRunning) {
            gifTask.resume()
        }
        /*if (lastDraw != null) {
            canvasView.getContext<CanvasRenderingContext2D>("2d").post(lastDraw!!)
        }*/
    }

    override fun onPause() {
        super.onPause()
        if (gifTask.isRunning) {
            gifTask.pause()
        }
        canvasView.onPause()
    }

}