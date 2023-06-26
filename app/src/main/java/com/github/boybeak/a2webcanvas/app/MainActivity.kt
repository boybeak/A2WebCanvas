package com.github.boybeak.a2webcanvas.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.github.boybeak.a2webcanvas.app.adapter.ApiItem
import com.github.boybeak.a2webcanvas.app.game.StickGame
import com.github.boybeak.adapter.AnyAdapter
import com.github.boybeak.adapter.event.OnItemClick
import com.github.boybeak.webcanvas.IWebCanvas
import com.github.boybeak.webcanvas.WebCanvasView
import com.github.boybeak.webcanvas.ext.post
import com.github.boybeak.webcanvas.twod.CanvasRenderingContext2D

class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }

    private val toolbar by lazy { findViewById<Toolbar>(R.id.toolbar) }
    private val canvasView by lazy { findViewById<WebCanvasView>(R.id.webCanvas) }
    private val apisRecyclerView by lazy { findViewById<RecyclerView>(R.id.apisRv) }

    private var lastDraw: (CanvasRenderingContext2D.() -> Unit)? = null

    private val gifTask by lazy { StickGame(canvasView) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.inflateMenu(R.menu.menu_main)
        toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.animationItem -> {
                    if (gifTask.isRunning) {
                        gifTask.finish()
                    } else {
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

        toolbar.menu.performIdentifierAction(R.id.autoItem, 0)

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
                    toolbar.title = item.source().name
                    canvasView.getContext<CanvasRenderingContext2D>("2d").post(item.source().onDraw)
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