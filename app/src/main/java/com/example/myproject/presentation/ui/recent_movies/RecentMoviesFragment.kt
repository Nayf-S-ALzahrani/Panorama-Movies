package com.example.myproject.presentation.ui.recent_movies

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import coil.load
import com.example.myproject.R
import com.example.myproject.databinding.RecentListItemBinding
import com.example.myproject.databinding.RecentMoviesFragmentBinding
import com.example.myproject.domain.model.recent_movies.ItemRecent
import com.example.myproject.presentation.recent_list.RecentListViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "RecentMoviesFragment"

@AndroidEntryPoint
class RecentMoviesFragment : Fragment() {

    private val recentViewModel by viewModels<RecentListViewModel>()
    private lateinit var binding: RecentMoviesFragmentBinding
    val state by lazy { recentViewModel.state.value }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val callback = object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                findNavController().navigate(R.id.action_recentMoviesFragment_to_firstFragment)
//            }
//        }
//        requireActivity().onBackPressedDispatcher.addCallback(callback)
        val snapHelper: SnapHelper = LinearSnapHelper()

        binding = RecentMoviesFragmentBinding.inflate(layoutInflater)
        binding.moviesRv.layoutManager =
            LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )

        snapHelper.attachToRecyclerView(binding.moviesRv)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recentViewModel.state.observe(viewLifecycleOwner) {
            when {
                it.recent.isNotEmpty() -> {
                    binding.moviesRv.adapter = RecentAdapter(it.recent)
                    Log.d(TAG, "The recent movies list: ${state?.recent}")
                }
                it.isLoading -> {
                    //show progress bar
                    Log.d(TAG, "Loading: ${state?.isLoading}")
                }
                else -> {
                    //toast or Snackbar error message
                    val snackbar =
                        Snackbar.make(requireView(), "Error", Snackbar.LENGTH_LONG)
                    snackbar.setAction("Dismiss") { snackbar.dismiss() }
                    snackbar.show()
                    Log.d(TAG, "Unknown error ${state?.error}")
                }
            }
        }
    }

    private inner class RecentHolder(val binding: RecentListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var postion = 0
        fun bind(recent: ItemRecent, position: Int) {
            this.postion = postion
            binding.titleTv.text = recent.title
            binding.posterIv.load(recent.image)
            Log.d(TAG, "bind: ${recent.image}")
            binding.backgroundImageView.load(recent.image)
            binding.releaseStateTv.text = recent.show_time
            binding.posterIv.setOnClickListener {
                val id = recent.theater_id
                val showId = bundleOf("showId" to id)
                findNavController().navigate(R.id.lastFragment, showId)
            }
        }
    }

    private inner class RecentAdapter(val recent: List<ItemRecent>) :
        RecyclerView.Adapter<RecentHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentHolder {
            val binding = RecentListItemBinding.inflate(
                layoutInflater,
                parent,
                false
            )
            return RecentHolder(binding)
        }

        override fun onBindViewHolder(holder: RecentHolder, position: Int) {
            val movie = recent[position]
            holder.bind(movie, position)
        }

        override fun getItemCount(): Int = recent.size
    }
}