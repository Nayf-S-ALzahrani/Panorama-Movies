package com.example.myproject.presentation.ui.splash_screen

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.myproject.R
import com.example.myproject.presentation.MainActivity

class SplashScreenActivity : AppCompatActivity() {
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        //this code delay 3 seconds splash screen
       Handler(Looper.myLooper()!!).postDelayed({
//           var icon_app:  = findViewById(R.raw.splash)
//           icon_app = findViewById(R.raw.splash)
//           icon_app.alpha = 1f
//           icon_app.animate().setDuration(1000)
//               .alpha(0f).withEndAction {
               val i = Intent(this, MainActivity::class.java)
               startActivity(i)
//               overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
               finish()
           },3500)
         }
    }