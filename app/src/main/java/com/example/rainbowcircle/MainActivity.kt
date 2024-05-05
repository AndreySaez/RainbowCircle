package com.example.rainbowcircle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import coil.load

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val resetButton = findViewById<Button>(R.id.btn_reset)
        val circle = findViewById<Circle>(R.id.circle)
        val orange = findViewById<ImageView>(R.id.iv_orange)
        val green = findViewById<ImageView>(R.id.iv_green)
        val blue = findViewById<ImageView>(R.id.iv_blue)

        circle.setResultListener { result ->
            when (result) {
                1 -> orange.load("https://placekitten.com/201/300")
                3 -> green.load("https://placekitten.com/200/300")
                5 -> blue.load("https://placekitten.com/199/300")
            }
        }
        resetButton.setOnClickListener {
            circle.reset()
            orange.setImageDrawable(null)
            green.setImageDrawable(null)
            blue.setImageDrawable(null)
        }

    }

}