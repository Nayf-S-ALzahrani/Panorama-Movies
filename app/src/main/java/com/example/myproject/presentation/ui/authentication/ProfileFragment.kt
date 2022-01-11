package com.example.myproject.presentation.ui.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.myproject.R
import com.example.myproject.databinding.ProfileFragmentBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception

class ProfileFragment : Fragment() {
    private lateinit var binding: ProfileFragmentBinding
    private var personaCollectionRef = Firebase.firestore.collection("persons")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProfileFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.save.setOnClickListener {
            checkInputs()
        }
    }
    private fun checkInputs(){
        val firstname = binding.edittextFirstname.text.toString()
        val lastname = binding.edittextLastname.text.toString()
        val age = binding.edittextAge.text.toString()

        when {
            (firstname.isNotEmpty() && lastname.isNotEmpty() && age.isNotEmpty()) -> {
                val person = Person(firstname, lastname, age)
                savePerson(person)
                val navController = findNavController()
                navController.navigate(R.id.homeFragment)
            }
            else ->
                Toast.makeText(context, "Fill all fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun savePerson(person: Person) = CoroutineScope(Dispatchers.IO).launch {
        try {
            personaCollectionRef.add(person).await()
            Toast.makeText(context, "Successfully saved data", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}