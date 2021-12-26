package com.example.myproject.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myproject.R
import com.example.myproject.presentation.ui.theater_movies.TheaterMoviesFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
//        if (currentFragment == null) {
//            val fragment = TheaterMoviesFragment()
//            supportFragmentManager.beginTransaction().add(R.id.fragmentContainerView, fragment)
//                .commit()
//        }
//
    }
}