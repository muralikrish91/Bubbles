package com.madrapps.bubbles

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.madrapps.bubbles.animators.VerticalStackedAnimator
import com.madrapps.bubbles.layouts.VerticalStackedLayout

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val menu = findViewById<ActionMenu>(R.id.menuLayout)
        menu.configure(VerticalStackedLayout(), VerticalStackedAnimator(applicationContext))

        var flag = true
        findViewById<View>(R.id.floatingActionButton).setOnClickListener({
            if (flag) {
                menu.open()
                flag = false
            } else {
                menu.close()
                flag = true
            }
        })
    }
}