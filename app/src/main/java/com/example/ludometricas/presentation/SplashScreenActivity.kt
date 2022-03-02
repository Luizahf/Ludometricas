package com.example.ludometricas.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.os.Handler
import android.view.WindowManager
import android.widget.ImageView
import com.example.ludometricas.R
import android.view.View
import android.view.animation.LinearInterpolator
import android.view.animation.Animation
import android.view.animation.RotateAnimation

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val rotate = RotateAnimation(
            0F,
            90F,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        rotate.duration = 2600
        rotate.interpolator = LinearInterpolator()

        val image = findViewById<View>(R.id.dotted_circle) as ImageView

        image.startAnimation(rotate)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        Handler().postDelayed(Runnable {
            val i = Intent(this@SplashScreenActivity, MainActivity::class.java)
            startActivity(i)
            finish()
        }, 3000)
    }
}