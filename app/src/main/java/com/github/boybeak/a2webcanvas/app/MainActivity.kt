package com.github.boybeak.a2webcanvas.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.github.boybeak.a2webcanvas.app.experiment.MultiThreadActivity
import com.github.boybeak.a2webcanvas.app.experiment.OnScreenActivity
import com.github.boybeak.a2webcanvas.app.ext.gotoActivity

class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.onScreenApi2D).setOnClickListener {
            gotoActivity(OnscreenActivity::class.java) {
                putString(OnscreenActivity.KEY_SUB_DIR, "2d")
            }
        }
        findViewById<View>(R.id.onScreenGame2D).setOnClickListener {
            gotoActivity(OnscreenActivity::class.java) {
                putString(OnscreenActivity.KEY_SUB_DIR, "game")
            }
        }
        findViewById<View>(R.id.offScreen2D).setOnClickListener {
            gotoActivity(OffscreenActivity::class.java)
        }
        findViewById<View>(R.id.multiThreadRender).setOnClickListener {
            gotoActivity(MultiThreadActivity::class.java)
        }
        findViewById<View>(R.id.newOnScreen).setOnClickListener {
            gotoActivity(OnScreenActivity::class.java)
        }
        findViewById<View>(R.id.oldV8Apis).setOnClickListener {
            gotoActivity(V8Activity::class.java)
        }

    }

}