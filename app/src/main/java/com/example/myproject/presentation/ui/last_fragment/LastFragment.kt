package com.example.myproject.presentation.ui.last_fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.myproject.R
import com.example.myproject.data.remote.dto.detail_dto.Actor
import com.example.myproject.data.remote.dto.detail_dto.Similar
import com.example.myproject.databinding.LastFragmentBinding
import com.example.myproject.databinding.SimilarsRecyclerviewBinding
import com.example.myproject.databinding.StarsRecyclerviewBinding
import com.example.myproject.presentation.show_detail.ShowDetailViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "LastFragment"

@AndroidEntryPoint
class LastFragment : Fragment() {

    private val lastViewModel by viewModels<ShowDetailViewModel>()
    private lateinit var binding: LastFragmentBinding
    val state by lazy { lastViewModel.state.value }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LastFragmentBinding.inflate(layoutInflater)
        binding.starRecyclerView.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.similarRecyclerView.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lastViewModel.state.observe(viewLifecycleOwner) { it ->
            when {
                it.show?.id?.isNotEmpty() == true -> {
                    binding.starRecyclerView.adapter = it.show.actorList?.let { it ->
                        ShowDetailAdapter(
                            it
                        )
                    }
                    binding.similarRecyclerView.adapter = it.show.similars?.let {
                        SimilarAdapter(it)
                    }
                    binding.animationView.visibility = View.INVISIBLE
                    binding.ageTv.text = it.show.age
                    binding.yearTv.text = it.show.year
                    binding.backgroundImageView.load(it.show.image)
                    binding.titleTv.text = it.show.title
                    binding.describeTv.text = it.show.plot
                    binding.posterIv.load(it.show.image)

                    binding.webView.webViewClient = WebViewClient()
                    binding.webView.loadUrl("${it.show.trailer?.linkEmbed}")
                    binding.webView.settings.javaScriptEnabled = true
                    binding.webView.settings.setSupportZoom(true)
                    binding.webView.settings.setAppCacheEnabled(true)
                    binding.webView.settings.builtInZoomControls = true
                    binding.webView.settings.saveFormData = true
                    binding.webView.setInitialScale(125)
                    binding.webView.settings.loadsImagesAutomatically = true
                    binding.webView.settings.mixedContentMode =
                        WebSettings.MIXED_CONTENT_ALWAYS_ALLOW


//                    binding.videoView.(showDetails.show.trailer?.linkEmbed)
                    binding.genresTv.text = it.show.genres
                    binding.releaseStateTv.text = it.show.time
                    binding.starsTv.text = it.show.stars

                    //                    binding.videoView.apply {
//                        val videoUrl = "https://clips.vorwaerts-gmbh.de/VfE_html5.mp4"
//
//                        videoUrl(videoUrl).addOnErrorListener(object : OnErrorListener {
//                            override fun onError(exception: FullscreenVideoViewException?) {
//                                Log.d(TAG, "onError: ${exception?.message}")                            }
//                        })
//                        enableAutoStart()

//                        val uri = Uri.parse(showDetails.show.trailer!!.linkEmbed)
//                        setVideoPath("${showDetails.show.trailer?.linkEmbed}.mp4")
//
//                        setMediaController(MediaController(requireContext()))
//                        Log.d(TAG, "onViewCreated: ${showDetails.show.trailer?.linkEmbed}")
//                        requestFocus()
//                        start()
//                    Log.d(TAG, "The detail: ${showDetails.show}")
                }
                it.isLoading -> {
                    //show progress bar
                    binding.animationView.visibility = View.VISIBLE
                    Log.d(TAG, "Loading: ${it.isLoading}")
                }
                it.error.isNotBlank() -> {
                    //toast error message
                    val snackBar =
                        Snackbar.make(requireView(), "Error Connection", Snackbar.LENGTH_LONG)
                    snackBar.setAction("Dismiss") { snackBar.dismiss() }
                    snackBar.show()
                    Log.d(TAG, "Error: ${it.error}")
                }
                else -> Log.d(TAG, "Unknown error ${it.error}")
            }
        }
    }

    //-----------------------Actors Images Holder and adapter-------------------------//

    private inner class ShowDetailHolder(val binding: StarsRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(starImage: Actor) {
            binding.starsMainRv.load(starImage.image)
            binding.starName.text = starImage.name
            Log.d(TAG, "bind: $starImage")
        }
    }

    private inner class ShowDetailAdapter(val starImages: List<Actor>) :
        RecyclerView.Adapter<ShowDetailHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowDetailHolder {
            val binding = StarsRecyclerviewBinding.inflate(
                layoutInflater,
                parent,
                false
            )
            return ShowDetailHolder(binding)
        }

        override fun onBindViewHolder(holder: ShowDetailHolder, position: Int) {
            val star = starImages[position]
            holder.bind(star)
            star.image
        }

        override fun getItemCount(): Int = starImages.size
    }

    //-----------------------Similar Holder and adapter-------------------------//

    private inner class SimilarHolder(val binding: SimilarsRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(similar: Similar?) {
            if (similar != null) {
                binding.similarRv.load(similar.image)
                binding.similarRv.setOnClickListener {
                    Log.d(TAG, "bind: work")
                    val id = similar.id
                    val showId = bundleOf("showId" to id)
                    findNavController().navigate(R.id.lastFragment, showId)
                    Log.d(TAG, "bind: after")
                }

                Log.d(TAG, "bind similar: $similar")
            } else {
                binding.similarRv.visibility = View.GONE
            }
        }
    }

    private inner class SimilarAdapter(val similar: List<Similar>) :
        RecyclerView.Adapter<SimilarHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarHolder {
            val binding = SimilarsRecyclerviewBinding.inflate(
                layoutInflater,
                parent,
                false
            )
            return SimilarHolder(binding)
        }

        override fun onBindViewHolder(holder: SimilarHolder, position: Int) {
            val same = similar[position]
            holder.bind(same)
        }

        override fun getItemCount(): Int = similar.size
    }

}