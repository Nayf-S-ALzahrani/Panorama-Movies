package com.example.myproject.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.compose.navArgument
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.myproject.R
import com.example.myproject.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navigationView: NavigationView
    lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        connectView()
        navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(navController.graph,drawerLayout)
        NavigationUI.setupActionBarWithNavController(this, navController,drawerLayout)
        NavigationUI.setupWithNavController(navigationView,navController)

//        navigationView.setNavigationItemSelectedListener {meuItem ->
//            when( meuItem.itemId) {
//                R.id.signOut -> {
//                    Firebase.auth.signOut()
//                    val navController = findNavController(R.id.signInFragment)
//                    navController.navigate(R.id.signInFragment)
//                    true
//                }else -> true
//            }



//        navigationView.setNavigationItemSelectedListener { menuItem->
//            when( menuItem.itemId){
//                R.id.signOut -> {
//
//                }


//            }
//        }
        }


    private fun connectView(){
        drawerLayout = binding.drawerLayout
        navigationView = binding.navigationView
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController,appBarConfiguration)
    }
// use repo (signut)

    override fun onStop() {
        super.onStop()
        Firebase.auth.signOut()
    }
}
