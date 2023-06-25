package com.github.boybeak.a2webcanvas.app.adapter

import android.view.View
import android.widget.TextView
import com.github.boybeak.a2webcanvas.app.R
import com.github.boybeak.adapter.AbsHolder
import com.github.boybeak.adapter.AnyAdapter

class ApiHolder(v: View) : AbsHolder<ApiItem>(v) {
    private val nameTv = v.findViewById<TextView>(R.id.apiName)
    override fun onBind(item: ApiItem, position: Int, absAdapter: AnyAdapter) {
        nameTv.text = item.source().name
    }
}