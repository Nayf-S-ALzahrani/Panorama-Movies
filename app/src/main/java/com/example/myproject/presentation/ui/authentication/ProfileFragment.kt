package com.example.myproject.presentation.ui.authentication

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.example.myproject.R
import com.example.myproject.databinding.ProfileFragmentBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "ProfileFragment"

class ProfileFragment : Fragment() {
    private lateinit var binding: ProfileFragmentBinding
    private var auth: FirebaseAuth = Firebase.auth
    private val userId = FirebaseAuth.getInstance().currentUser?.uid
    private var personaCollectionRef = userId?.let {
        Firebase.firestore.collection("persons").document(
            it
        )
    }

    private var imageProfile: Uri? = null
    private val getResult = registerForActivityResult(ActivityResultContracts.GetContent()) {
        imageProfile = it
        binding.imageView3.setImageURI(it)
    }

    private val dataBase = FirebaseFirestore.getInstance()

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        Toast.makeText(context, "${auth.currentUser?.uid}", Toast.LENGTH_SHORT).show()
        updateProfileText()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProfileFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK && data != null && data.data != null) {
            imageProfile = data.data!!
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageView3.setOnClickListener {
            getResult.launch("image/*")
            hideKeyboard()
        }

        binding.save.setOnClickListener {
            checkInputs()
            //saveImage()
            hideKeyboard()
        }

        binding.delete.setOnClickListener {
            deleteAccount()
        }

        binding.updateProfile.setOnClickListener {
            updateProfileText()
            hideKeyboard()
            if (userId != null) {
                val docRef = dataBase.collection("persons").document(userId)
                docRef.get()
                    .addOnSuccessListener { document ->
                        if (document != null) {
                            binding.textView3.text = document.data.toString()
                            Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                        } else {
                            Log.d(TAG, "No such document")
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.d(TAG, "get failed with ", exception)
                    }
            }
        }
    }

    private fun checkInputs() {
        val nickname = binding.edittextNickname.text.toString()
        val imageProfile = binding.imageView3.toString()
        when {
            (nickname.isNotEmpty()) -> {
                val person = (userId?.let { Person(it, nickname) })
                if (person != null) {
                    savePerson(person)
                }
                val navController = findNavController()
                //  navController.navigate(R.id.homeFragment)
            }
            else ->
                Toast.makeText(context, "Fill all fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveImage() {
        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val day = Date()
        val fileName = formatter.format(day)
        val storage = FirebaseStorage.getInstance()
        val file = "image/$fileName"
        val send = storage.getReference(file)
        val uploadtask = send.putFile(imageProfile!!)
        uploadtask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let { throw it }
                Log.d(TAG, "saveImage: uri is Successful ")
            }
            send.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                task.result
                Log.d(TAG, "saveImage: download uri")
                val ref = userId?.let { dataBase.collection("persons").document(it) }
                ref?.update("imageProfile", task.result.toString())
                Log.d(TAG, "saveImage: connect with user")
            }
        }
    }

    private fun updateProfileText() {
        auth.currentUser?.let { user ->
            val nickname = binding.edittextNickname.text.toString()
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(nickname)
                .build()
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    user.updateProfile(profileUpdates).await()
                    withContext(Dispatchers.Main) {
                        checkLoggedState()
                        Toast.makeText(
                            context,
                            "Successfully updated user profile ",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }

    private fun savePerson(person: Person) = CoroutineScope(Dispatchers.IO).launch {
        try {
            personaCollectionRef?.set(person)?.await()
            Toast.makeText(context, "Successfully saved data", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun deleteAccount() = CoroutineScope(Dispatchers.IO).launch {
        try {
            Firebase.auth.currentUser?.delete()?.addOnCompleteListener { task ->
                when {
                    task.isSuccessful -> {
                        Toast.makeText(context, "Your account deleted", Toast.LENGTH_SHORT).show()
                        val navController = findNavController()
                        navController.navigate(R.id.registerFragment)
                    }
                    task.isCanceled -> {
                        Toast.makeText(context, "Complete", Toast.LENGTH_SHORT).show()
                    }
                    else ->
                        Toast.makeText(context, "$userId", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkLoggedState() {
        val user = auth.currentUser?.email
        if (user == null) {
            Toast.makeText(context, "You are not login!", Toast.LENGTH_SHORT).show()
        } else {
            binding.textView3.text = user
            Toast.makeText(context, "You are logged in", Toast.LENGTH_SHORT).show()
        }
    }

    fun ProfileFragment.hideKeyboard() {
        val inputManager =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}
