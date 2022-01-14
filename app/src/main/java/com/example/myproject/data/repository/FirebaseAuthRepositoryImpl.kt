package com.example.myproject.data.repository

import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class FirebaseAuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {
}