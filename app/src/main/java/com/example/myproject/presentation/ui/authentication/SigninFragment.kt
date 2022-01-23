package com.example.myproject.presentation.ui.authentication

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.myproject.R
import com.example.myproject.databinding.SigninFragmentBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

private const val TAG = "SignInFragment"

class SignInFragment : Fragment() {
    private lateinit var binding: SigninFragmentBinding
    private lateinit var auth: FirebaseAuth

    override fun onStart() {
        super.onStart()
        checkLoggedState()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SigninFragmentBinding.inflate(layoutInflater)
        auth = FirebaseAuth.getInstance()
        auth.signOut()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context)
        val email = sharedPreferences.getString("Email", "")
        val password = sharedPreferences.getString("Password", "")
        binding.etEmailSignIn.setText(email)
        binding.etPasswordSignIn.setText(password.toString())

        binding.tvSigninLink.setOnClickListener {
            findNavController().navigate(R.id.registerFragment)
        }

        binding.buttonSignIn.setOnClickListener {
            loginUser()
        }

    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onStop() {
        super.onStop()
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
    }

    private fun saveInSharedPreferences() {
        val sharedPreferences: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.putString("Email", binding.etEmailSignIn.text.toString())
        editor.putString("Password", binding.etPasswordSignIn.text.toString())
        editor.apply()
        Toast.makeText(context, getString(R.string.remember_me_toast), Toast.LENGTH_SHORT)
            .show()
    }

    fun isNameFormat(name:String?) =
        (name.isNullOrBlank() || name.length > 25 ).not()


    private fun loginUser() {
        val email = binding.etEmailSignIn.text.toString()
        val password = binding.etPasswordSignIn.text.toString()

        when {
            (email.isEmpty() || password.isEmpty()) -> Toast.makeText(
                context,
                getString(R.string.login_fill_toast),
                Toast.LENGTH_LONG
            ).show()

            (email.isNotEmpty() && password.isNotEmpty()) -> CoroutineScope(Dispatchers.IO).launch {
                try {
                    Log.d(TAG, "registerUser: email $email")
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                binding.buttonSignIn.isEnabled = false
                                binding.buttonSignIn.alpha = 0.5f
                                hideKeyboard()

                                Toast.makeText(
                                    context,
                                    getString(R.string.welcome_toast),
                                    Toast.LENGTH_LONG
                                ).show()

                                if (binding.checkBox.isChecked) {
                                    saveInSharedPreferences()
                                }

                                findNavController().navigate(R.id.homeFragment)

                            } else {
                                hideKeyboard()
                                Toast.makeText(
                                    context,
                                    "${task.exception?.message}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        hideKeyboard()
                        Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun checkLoggedState() {
        if (auth.currentUser == null) {
            Toast.makeText(context, getString(R.string.not_login_toast), Toast.LENGTH_SHORT)
                .show()
        } else {
            Toast.makeText(context, getString(R.string.login_toast), Toast.LENGTH_SHORT).show()
        }
    }

    private fun SignInFragment.hideKeyboard() {
        val inputManager =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}
