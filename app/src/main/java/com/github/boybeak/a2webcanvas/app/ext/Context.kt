package com.github.boybeak.a2webcanvas.app.ext

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle

fun <T : Activity> Context.gotoActivity(activityClz: Class<T>, extras: (Bundle.() -> Unit)? = null) {
    val intent = Intent(this, activityClz)
    if (extras != null) {
        val bundle = intent.extras ?: Bundle()
        extras.invoke(bundle)
        intent.putExtras(bundle)
    }
    startActivity(intent)
}