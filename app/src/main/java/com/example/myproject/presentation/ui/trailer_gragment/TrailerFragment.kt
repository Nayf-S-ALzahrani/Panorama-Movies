//package com.example.myproject.presentation.ui.trailer_gragment
//
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.webkit.WebView
//import androidx.fragment.app.viewModels
//import com.example.myproject.R
//import com.example.myproject.databinding.LastFragmentBinding
//import com.example.myproject.databinding.TrailerFragmentBinding
//import com.example.myproject.presentation.show_detail.ShowDetailViewModel
//import com.example.myproject.presentation.webview_list.WebViewListViewModel
//import dagger.hilt.android.AndroidEntryPoint
//
//private const val TAG = "TrailerFragment"
//
//@AndroidEntryPoint
//class TrailerFragment : Fragment() {
//
//    private val trailerViewModel by viewModels<WebViewListViewModel>()
//    private lateinit var binding: TrailerFragmentBinding
//    val state by lazy { trailerViewModel.state.value }
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        binding = TrailerFragmentBinding.inflate(layoutInflater)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        trailerViewModel.state.observe(viewLifecycleOwner){
//            when{
//                it.trailer?.id?.isNotEmpty() == true -> {
//                    binding.webView.url = it.trailer.trailer.
//
//
//                }
//            }
//        }
//
//    }
//    private fun loadVideo(webView:WebView){
//        val  = "${binding.}"
//
//        val embedString = "<iframe  src=\"$videoUrl\" frameborder=\"0\" allowfullscreen></iframe>"
//        webView.webChromeClient = WebChromeClient()
//        val webSettings = webView.settings
//        webSettings.javaScriptEnabled = true
//        webView.settings.loadWithOverviewMode = true
//
//        var text = "<html><body style=\"text-align:justify;\">"
//        text += embedString
//        text += "</body></html>"
//        webView.loadData(text, "text/html", "utf-8")
//    }
//}
//
//
