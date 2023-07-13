package com.github.boybeak.a2webcanvas.app.game

class GameEngine<T : GamePlayground>(private val creator: (name: String) -> T) {

    private var currentPlayground: T? = null

    val playground: T get() = currentPlayground!!

    fun createPlayground(name: String, onFinish: OnStartFinishListener): GamePlayground {
        if (currentPlayground != null) {
            destroyPlayground()
        }
        currentPlayground = creator.invoke(name)
        currentPlayground?.start(onFinish)
        return currentPlayground!!
    }

    fun destroyPlayground() {
        currentPlayground?.stop()
        currentPlayground = null
    }

}