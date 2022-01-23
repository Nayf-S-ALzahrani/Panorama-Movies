package com.example.myproject.presentation

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import coil.load
import com.example.myproject.R
import com.example.myproject.databinding.ActivityMainBinding
import com.example.myproject.presentation.ui.IOnBackPressed
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navigationView: NavigationView
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        connectView()
        setupDrawer()
        drawerClicks()
//        updateHeaderEmail()
//        updateHeaderUsername()

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
        Firebase.auth.signOut()
    }

    private fun drawerClicks() {
        binding.navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.profileFragment -> {
                    Toast.makeText(this, getString(R.string.your_profile), Toast.LENGTH_SHORT).show()
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }

                R.id.signInFragment -> {
                    Firebase.auth.signOut()
                    Toast.makeText(this, getString(R.string.toenter), Toast.LENGTH_SHORT).show()
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }

                R.id.homeFragment -> {
                    Toast.makeText(this, getString(R.string.start), Toast.LENGTH_SHORT).show()
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                else -> true

            }
        }
    }
}

//    private fun updateHeaderEmail() {
//        val header = navigationView.getHeaderView(0)
//        val textViewEmail = header.findViewById<TextView>(R.id.email)
//        auth = FirebaseAuth.getInstance()
//        textViewEmail.text = auth.currentUser?.email
//    }

//    private fun updateHeaderUsername() {
//        val header = navigationView.getHeaderView(0)
//        val textViewUsername = header.findViewById<TextView>(R.id.userName)
//        auth = FirebaseAuth.getInstance()
//        textViewUsername.text = auth.currentUser?.displayName
//
//        val d = FirebaseFirestore.getInstance()
//        d.collection("persons").document(userId!!)
//            .get().addOnCompleteListener {
//                if (it.result?.exists()!!) {
//
//
//                    val nickname = it.result!!.getString("nickname")
//                    val firstName = it.result!!.getString("firstName")
//                    val lastName = it.result!!.getString("lastName")
//                    val age = it.result!!.get("age")
//                    val image = it.result!!.getString("imageProfile")
//
//                    if (nickname != null && firstName != null && lastName != null && age != null) {
//                        textViewUsername.text = nickname
////                        binding.ageTextview.text = age.toString()
////                        binding.nicknameTv.text = nickname
////                        binding.lastnameTextview.text = lastName
////                        binding.imag.load(image)
//                    }

//    private fun updateHeaderImage(){
//        val header = navigationView.getHeaderView(0)
//        val textViewImage = header.findViewById<TextView>(R.id.image_profile)
//        auth = FirebaseAuth.getInstance()
//        textViewImage.text = auth.currentUse
//    }

//    override fun onBackPressed() {
//        val fragment =
//            this.supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container)
//        (fragment as? IOnBackPressed)?.onBackPressed()?.not()?.let {
//            tra
//            super.onBackPressed()
//        }
//    }

