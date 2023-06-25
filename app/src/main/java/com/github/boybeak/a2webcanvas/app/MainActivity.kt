package com.github.boybeak.a2webcanvas.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.SpinnerAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.github.boybeak.a2webcanvas.app.adapter.ApiItem
import com.github.boybeak.adapter.AnyAdapter
import com.github.boybeak.adapter.event.OnClick
import com.github.boybeak.adapter.event.OnItemClick
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

    private val toolbar by lazy { findViewById<Toolbar>(R.id.toolbar) }
    private val canvasView by lazy { findViewById<WebCanvasView>(R.id.webCanvas) }
    private val apisRecyclerView by lazy { findViewById<RecyclerView>(R.id.apisRv) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.inflateMenu(R.menu.menu_main)
        toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
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
                }
            })
        }

    }

}