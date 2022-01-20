package com.example.myproject.presentation.ui.authentication

import org.junit.Assert.*
import org.junit.Test

class SignInFragmentTest {

    @Test
    fun isNameFormat() {
        val name = SignInFragment().isNameFormat("Naif")
        assertEquals(name,true)
    }
}