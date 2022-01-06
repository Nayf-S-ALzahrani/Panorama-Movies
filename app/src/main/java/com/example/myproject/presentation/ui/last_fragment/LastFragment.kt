package com.example.myproject.presentation.ui.last_fragment

import android.net.Uri
import android.os.Bundle
import android.support.v4.media.session.MediaControllerCompat.setMediaController
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import coil.load
import coil.loadAny
import com.example.myproject.databinding.LastFragmentBinding
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lastViewModel.state.observe(viewLifecycleOwner) { showDetails ->
            when {
                showDetails.show?.id?.isNotEmpty() == true -> {
                    binding.animationView.visibility = View.INVISIBLE
                    binding.ageTv.text = showDetails.show.age
                    binding.backgroundImageView.load(showDetails.show.image)
                    binding.titleTv.text = showDetails.show.title
                    binding.describeTv.text = showDetails.show.plot
                    binding.posterIv.load(showDetails.show.image)
//                    binding.videoView.(showDetails.show.trailer?.linkEmbed)
                    binding.genresTv.text = showDetails.show.genres
                    binding.releaseStateTv.text = showDetails.show.time
                    binding.starsTv.text = showDetails.show.stars
//binding.posterIv.load(showDetails.show.trailer?.thumbnailUrl)
                    Log.d(TAG, "onViewCreated: ${showDetails.show.trailer?.linkEmbed}")
                    binding.videoView.apply {
                        val uri = Uri.parse(showDetails.show.trailer!!.linkEmbed)
                        setVideoURI(uri)
                        setMediaController(MediaController(requireContext()))
                        Log.d(TAG, "onViewCreated: ${showDetails.show.trailer.linkEmbed}")
                        requestFocus()
                        start()
                    }
                    Log.d(TAG, "The detail: ${showDetails.show}")
                }
                showDetails.isLoading -> {
                    //show progress bar
                    binding.animationView.visibility = View.VISIBLE
//                    Log.d(TAG, "Loading: ${showDetails.isLoading}")
                }
                showDetails.error.isNotBlank() -> {
                    //toast error message
                    val snackbar =
                        Snackbar.make(requireView(), "Error Connection", Snackbar.LENGTH_LONG)
                    snackbar.setAction("Dismiss") { snackbar.dismiss() }
                    snackbar.show()
                    Log.d(TAG, "Error: ${showDetails.error}")
                }
                else -> Log.d(TAG, "Unknown error ${showDetails.error}")
            }
        }
    }
}