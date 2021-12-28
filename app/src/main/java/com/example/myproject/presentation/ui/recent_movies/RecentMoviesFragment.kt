package com.example.myproject.presentation.ui.recent_movies

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.view.SupportActionModeWrapper
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
import com.example.myproject.presentation.ui.all_movies_and_series.FirstFragment
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "RecentMoviesFragment"

@AndroidEntryPoint
class RecentMoviesFragment : Fragment() {

    companion object {
        fun newInstance() = RecentMoviesFragment()
    }

    private val recentViewModel by viewModels<RecentListViewModel>()
    private lateinit var binding: RecentMoviesFragmentBinding
    val state by lazy { recentViewModel.state.value }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_recentMoviesFragment_to_firstFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
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
            binding.moviesRv.adapter = RecentAdapter(it.recent)
            Log.d(TAG, "onViewCreated: ${it.recent}")
        }
//        if (state.isNotBlank()){
//            Log.d(TAG, "onViewCreated: ${state.error}")
//        }
//        if (state.isLoading){
//            Log.d(TAG, "onViewCreated: ${state.isLoading}")
//        }
//        if (state.recents.isNotEmpty()){
//            binding.moviesRv.adapter = RecentAdapter(state.recents)
//            Log.d(TAG, "onViewCreated: ${state}")
//            Log.d(TAG, "onViewCreated: ${state.recents}")
//        }
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