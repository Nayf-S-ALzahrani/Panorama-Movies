package com.example.myproject

import android.content.Intent
import android.graphics.drawable.Icon
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import com.example.myproject.presentation.MainActivity
import kotlinx.coroutines.delay

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        //this code delay 3 seconds splash screen
       Handler(Looper.myLooper()!!).postDelayed({
           var icon_app: ImageView? = null
           icon_app = findViewById(R.id.splash_screen)
           icon_app.alpha = 1f
           icon_app.animate().setDuration(2000).alpha(0f).withEndAction {
               val i = Intent(this,MainActivity::class.java)
               startActivity(i)
               overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
               finish()}
           },2250)
         }
    }