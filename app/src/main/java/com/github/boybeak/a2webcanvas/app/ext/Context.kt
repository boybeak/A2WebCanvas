package com.github.boybeak.a2webcanvas.app.ext

import android.app.Activity
import android.content.Context
import android.content.Intent

fun <T : Activity> Context.gotoActivity(activityClz: Class<T>) {
    startActivity(Intent(this, activityClz))
}