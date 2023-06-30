package com.github.boybeak.webcanvas.render

interface EventPoster {
    fun postEvent(event: Runnable)
    fun postEventDelayed(delayInMills: Long, event: Runnable)
    fun removeEvent(event: Runnable)
}