package com.example.myproject.presentation.webview_list

import com.example.myproject.domain.model.detail.ShowDetail

data class WebViewValue (
val isLoading: Boolean = false,
val trailer: ShowDetail? = null ,
val error: String = "",
)
