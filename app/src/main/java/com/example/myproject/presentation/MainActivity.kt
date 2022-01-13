package com.example.myproject.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.myproject.R
import com.example.myproject.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navigationView: NavigationView
    lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        connectView()
        setupDrawer()
        drawerClicks()

        navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        NavigationUI.setupWithNavController(binding.navigationView, navController)
    }

    private fun setupDrawer() {
        val toggleDrawer =
            ActionBarDrawerToggle(this, drawerLayout, R.string.app_name, R.string.app_name)
        drawerLayout.addDrawerListener(toggleDrawer)
        toggleDrawer.syncState()
    }

    private fun connectView() {
        drawerLayout = binding.drawerLayout
        navigationView = binding.navigationView
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }

    // use repo (signout)
    override fun onStop() {
        super.onStop()
        //Firebase.auth.signOut()
    }

    private fun drawerClicks() {
        binding.navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.profileFragment -> {
                    Toast.makeText(this, "Your profile", Toast.LENGTH_SHORT).show()
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }

                R.id.signInFragment -> {
                    Firebase.auth.signOut()
                    Toast.makeText(this, "signin", Toast.LENGTH_SHORT).show()
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }

                R.id.homeFragment -> {
                    Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                else -> true

            }
        }
    }
}

