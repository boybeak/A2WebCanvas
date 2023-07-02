package com.github.boybeak.v8webcanvas.image

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.util.Log
import com.eclipsesource.v8.V8
import com.eclipsesource.v8.V8Function
import com.eclipsesource.v8.V8Object
import com.github.boybeak.v8x.binding.Key
import com.github.boybeak.v8x.binding.V8Binding
import com.github.boybeak.v8x.binding.annotation.V8Field
import com.github.boybeak.v8x.binding.annotation.V8Method
import com.github.boybeak.webcanvas.image.HTMLImageElement

class V8HTMLImageElement(private val htmlImageElement: HTMLImageElement) : V8Binding {

    companion object {
        private const val TAG = "V8HTMLImageElement"
        private const val KEY_HTML_IMAGE_ELEMENT_ID = "_v8HTMLImageElementId"
        fun getId(v8obj: V8Object): String {
            return v8obj.getString(KEY_HTML_IMAGE_ELEMENT_ID)
        }
    }

    @V8Field
    private val _v8HTMLImageElementId: String = htmlImageElement.id

    private var v8: V8? = null
    private var v8Looper: Looper? = null
    private val v8Handler: Handler by lazy { Handler(v8Looper!!) }

    override fun getBindingId(): String {
        return _v8HTMLImageElementId
    }

    override fun onBindingChanged(
        target: V8Object,
        fieldInfo: Key,
        newValue: Any?,
        oldValue: Any?
    ) {}

    override fun getAdditionalPropertyNames(): Array<String> {
        return arrayOf("src", "onload", "load", "onerror", "error")
    }

    override fun onAdditionalPropertyChanged(
        target: V8Object,
        propertyName: String,
        newValue: Any?,
        oldValue: Any?
    ) {
        if (v8 == null) {
            v8 = target.runtime
        }
        if (v8Looper == null && Thread.currentThread() is HandlerThread) {
            v8Looper = Looper.myLooper()
        }
        when(propertyName) {
            "src" -> htmlImageElement.src = newValue.toString()
            "onload", "load" -> htmlImageElement.setOnLoad {
                if (htmlImageElement.bitmap != null) {
                    if (v8Looper != null) {
                        v8Handler.post {
                            getMyBinding(v8!!).executeJSFunction(propertyName)
                        }
                    } else {
                        getMyBinding(v8!!).executeJSFunction(propertyName)
                    }
                }
            }
            "onerror", "error" -> htmlImageElement.setOnError {
                target.executeJSFunction("onerror")
            }
        }
    }

    @V8Method
    fun addEventListener(name: String, function: V8Function) {
        this.getMyBinding(function.runtime).add(name, function)
    }

}