package com.example.myproject.presentation.ui.authentication

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import coil.load
import com.example.myproject.R
import com.example.myproject.databinding.ProfileFragmentBinding
import com.google.firebase.auth.FirebaseAuth
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
    }

    private val dataBase = FirebaseFirestore.getInstance()

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
//        Toast.makeText(context, "${auth.currentUser?.uid}", Toast.LENGTH_SHORT).show()
        getUserInfo(userId!!)
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

        binding.imag.setOnClickListener {
            checkLoggedState()
            getResult.launch("image/*")
            implecentImage()
            hideKeyboard()
        }

        binding.save.setOnClickListener {
            checkLoggedState()
            checkInputs()
            implecentImage()
            hideKeyboard()
        }

        binding.updat.setOnClickListener {
            checkLoggedState()
            implecentImage()
            hideKeyboard()
        }

        binding.delete.setOnClickListener {
            checkLoggedState()
            deleteAccount()
        }

        //--------------------------Nick name-------------------------------//

        binding.iconUpdateNickname.setOnClickListener {
            binding.iconSaveNickname.visibility = View.VISIBLE
            binding.edittextNickname.text = binding.nicknameTv.editableText
            binding.edittextNickname.visibility = View.VISIBLE
        }

        binding.iconSaveNickname.setOnClickListener {
            val nickname = binding.edittextNickname.text.toString()
            when {
                (nickname.isEmpty()) -> {
                    Toast.makeText(
                        context,
                        "Enter nick name",
                        Toast.LENGTH_SHORT

                    ).show()
                    binding.iconSaveNickname.visibility = View.INVISIBLE
                    binding.edittextNickname.visibility = View.INVISIBLE
                }
                (nickname.isNotEmpty()) -> {
                    binding.iconSaveNickname.visibility = View.INVISIBLE
                    binding.nicknameTv.visibility = View.VISIBLE
                    dataBase.collection("persons").document(userId!!)
                        .update("nickname", nickname)
                    getUserInfo(userId)
                    hideKeyboard()
                    Toast.makeText(context, "Successful update", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        //--------------------------First name-------------------------------//

        binding.iconUpdateFirstname.setOnClickListener {
            binding.iconSaveFirstname.visibility = View.VISIBLE
            binding.edFirstName.text = binding.firstnameTextview.editableText
            binding.edFirstName.visibility = View.VISIBLE
        }

        binding.iconSaveFirstname.setOnClickListener {
            val firstname = binding.edFirstName.text.toString()
            when {
                (firstname.isEmpty()) -> Toast.makeText(
                    context,
                    "Enter first name",
                    Toast.LENGTH_SHORT
                ).show()
                (firstname.isNotEmpty()) -> {
                    binding.iconSaveFirstname.visibility = View.INVISIBLE
                    binding.firstnameTextview.visibility = View.VISIBLE
                    dataBase.collection("persons").document(userId!!)
                        .update("firstName", firstname)
                    getUserInfo(userId)
                    hideKeyboard()
                    Toast.makeText(context, "Successful update", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        //--------------------------Last name-------------------------------//

        binding.iconUpdateLastname.setOnClickListener {
            binding.iconSaveLastname.visibility = View.VISIBLE
            binding.edittextLastname.text = binding.lastnameTextview.editableText
            binding.edittextLastname.visibility = View.VISIBLE
        }

        binding.iconSaveLastname.setOnClickListener {
            val lastname = binding.edittextLastname.text.toString()
            when {
                (lastname.isEmpty()) -> Toast.makeText(
                    context,
                    "Enter last name",
                    Toast.LENGTH_SHORT
                ).show()
                (lastname.isNotEmpty()) -> {
                    binding.iconSaveLastname.visibility = View.INVISIBLE
                    binding.lastnameTextview.visibility = View.VISIBLE
                    dataBase.collection("persons").document(userId!!)
                        .update("lastName", lastname)
                    getUserInfo(userId)
                    hideKeyboard()
                    Toast.makeText(context, "Successful update", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        //--------------------------Age-------------------------------//

        binding.iconUpdateAge.setOnClickListener {
            binding.iconSaveAge.visibility = View.VISIBLE
            binding.edittextAge.text = binding.ageTextview.editableText
            binding.edittextAge.visibility = View.VISIBLE
        }

        binding.iconSaveAge.setOnClickListener {
            val age = binding.edittextAge.text.toString()
            when {
                (age.isEmpty()) -> Toast.makeText(
                    context,
                    "Enter your age",
                    Toast.LENGTH_SHORT
                ).show()
                (age.isNotEmpty()) -> {
                    binding.iconSaveAge.visibility = View.INVISIBLE
                    binding.ageTextview.visibility = View.VISIBLE
                    dataBase.collection("persons").document(userId!!)
                        .update("age", age)
                    getUserInfo(userId)
                    hideKeyboard()
                    Toast.makeText(context, "Successful update", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun checkInputs() {
        val nickname = binding.edittextNickname.text.toString()
        val firstName = binding.edFirstName.text.toString()
        val lastName = binding.edittextLastname.text.toString()
        val age = binding.edittextAge.text.toString()
        val imageProfile = binding.imag.toString()

        val person = (userId?.let {
            Person(
                it,
                nickname,
                firstName,
                lastName,
                age.toInt(),
                imageProfile
            )
        })
        if (person != null) {
            savePerson(person)
        }
        val navController = findNavController()
        navController.navigate(R.id.homeFragment)
    }

    private fun getUserInfo(userId: String) {

        val d = FirebaseFirestore.getInstance()
        d.collection("persons").document(userId)
            .get().addOnCompleteListener {
                if (it.result?.exists()!!) {
                    binding.edFirstName.visibility = View.GONE
                    binding.edittextNickname.visibility = View.GONE
                    binding.edittextLastname.visibility = View.GONE
                    binding.edittextAge.visibility = View.GONE
                    binding.save.visibility = View.GONE

                    val nickname = it.result!!.getString("nickname")
                    val firstName = it.result!!.getString("firstName")
                    val lastName = it.result!!.getString("lastName")
                    val age = it.result!!.get("age")
                    val image = it.result!!.getString("imageProfile")

                    if (nickname != null && firstName != null && lastName != null && age != null) {
                        binding.firstnameTextview.text = firstName
                        binding.ageTextview.text = age.toString()
                        binding.nicknameTv.text = nickname
                        binding.lastnameTextview.text = lastName
                        binding.imag.load(image)

//                        Toast.makeText(context, "$nickname", Toast.LENGTH_LONG).show()
                    }
                } else {
//                    Toast.makeText(context, "no Value", Toast.LENGTH_LONG).show()
                }
            }.addOnFailureListener {
                Log.d(TAG, "getUserInfo:  FailureListener")
            }
    }

    private fun implecentImage() {
        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val day = Date()
        val fileName = formatter.format(day)
        val storage = FirebaseStorage.getInstance()
        val file = "image/$fileName"
        val send = storage.getReference(file)
        imageProfile?.let { image ->
            val uploadtask = send.putFile(image)

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
                    Toast.makeText(context, "Image saved", Toast.LENGTH_SHORT).show()
                    Log.d(TAG, "saveImage: connect with user")
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
                        Toast.makeText(context, "Your account deleted", Toast.LENGTH_SHORT)
                            .show()
                        FirebaseFirestore.getInstance().collection("persons").document(userId!!)
                            .delete()
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
        val sharedPreferences: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context)
        sharedPreferences.edit().remove("Email").apply()
        sharedPreferences.edit().remove("Password").apply()
    }

    private fun checkLoggedState() {
        val user = auth.currentUser?.email
        if (user == null) {
            Log.d(TAG, "checkLoggedState: $user")
//            Toast.makeText(context, "You are not login!", Toast.LENGTH_SHORT).show()
        } else {
//            binding.textView3.text = user
            Toast.makeText(context, "You are logged in", Toast.LENGTH_SHORT).show()
        }
    }

    fun ProfileFragment.hideKeyboard() {
        val inputManager =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}
