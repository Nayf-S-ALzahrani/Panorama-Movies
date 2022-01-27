package com.example.myproject.presentation.ui.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.myproject.R
import com.example.myproject.databinding.RegisterFragmentBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import java.lang.Exception

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private lateinit var binding: RegisterFragmentBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RegisterFragmentBinding.inflate(layoutInflater)
        auth = FirebaseAuth.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvLoginLink.setOnClickListener {
            val navController = findNavController()
            navController.navigate(R.id.signInFragment)
        }

        binding.buttonLogin.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val email = binding.edittextEmailLogin.text.toString()
        val password = binding.edittextPasswordLogin.text.toString()
        val confirmPassword = binding.edittextRepassword.text.toString()
        when {
            (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) -> Toast.makeText(
                context,
                getString(R.string.fill_field),
                Toast.LENGTH_LONG
            ).show()

            password != confirmPassword -> Toast.makeText(
                context,
                getString(R.string.password_match),
                Toast.LENGTH_SHORT
            ).show()

            (email.isNotEmpty() && password.isNotEmpty()) -> CoroutineScope(Dispatchers.IO).launch {
                try {
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(context, getString(R.string.register_r), Toast.LENGTH_LONG)
                                    .show()
                                binding.buttonLogin.isEnabled = false
                                binding.buttonLogin.alpha = 0.5f
                                val navController = findNavController()
                                navController.navigate(R.id.signInFragment)
                            } else {
                                Toast.makeText(
                                    context,
                                    "${task.exception?.message}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

}
