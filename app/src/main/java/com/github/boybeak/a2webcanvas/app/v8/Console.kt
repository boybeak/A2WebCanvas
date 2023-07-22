package com.github.boybeak.a2webcanvas.app.v8

import android.util.Log
import com.github.boybeak.v8x.binding.V8BindingAdapter
import com.github.boybeak.v8x.binding.annotation.V8Method

class Console : V8BindingAdapter {

    private val tag = "V8Console"

    override fun getBindingId(): String {
        return this.hashCode().toString()
    }

    @V8Method
    fun log(vararg args: Any) {
        Log.d(tag, args.joinToString(separator = ""))
    }
}