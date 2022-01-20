package com.example.myproject.presentation.ui.last_fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.myproject.R
import com.example.myproject.data.remote.dto.detail_dto.Actor
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lastViewModel.state.observe(viewLifecycleOwner) {
            when {
                it.show?.id?.isNotEmpty() == true -> {
                    binding.starRecyclerView.adapter = it.show.actorList?.let { it ->
                        ShowDetailAdapter(
                            it
                        )
                    }
                    binding.similarRecyclerView.adapter = SimilarAdapter(it.show.similars)
                    binding.animationView.visibility = View.INVISIBLE
                    binding.ageTv.text = it.show.age
                    binding.backgroundImageView.load(it.show.image)
                    binding.titleTv.text = it.show.title
                    binding.describeTv.text = it.show.plot
                    binding.posterIv.load(it.show.image)
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
        fun bind(similar: List<String>?) {
            if (similar != null) {
                binding.similarRv.load(similar[layoutPosition])

//                val inPosition = bundleOf("id" to id)
//                findNavController().navigate(R.id.recentMoviesFragment, inPosition)
                Log.d(TAG, "bind similar: $similar")
            } else {
                binding.similarRv.visibility = View.GONE
            }
        }
    }

    private inner class SimilarAdapter(val similar: List<String>?) :
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
            val same = similar
            holder.bind(same)
        }

        override fun getItemCount(): Int = similar!!.size
    }

}