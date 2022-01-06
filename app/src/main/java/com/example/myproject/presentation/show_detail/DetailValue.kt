package com.example.myproject.presentation.show_detail

import com.example.myproject.domain.model.detail.ShowDetail

data class DetailValue (
    val isLoading: Boolean = false,
    val show: ShowDetail? = null ,
    val error: String = ""
)