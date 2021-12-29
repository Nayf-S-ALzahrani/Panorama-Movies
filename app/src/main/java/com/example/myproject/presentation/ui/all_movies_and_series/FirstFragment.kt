package com.example.myproject.presentation.ui.all_movies_and_series

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.myproject.R
import com.example.myproject.databinding.HomeBinding
import com.example.myproject.databinding.RecentRecyclerviewBinding
import com.example.myproject.domain.model.recent_movies.ItemRecent
import com.example.myproject.presentation.recent_list.RecentListViewModel
import com.example.myproject.presentation.ui.recent_movies.RecentMoviesFragment
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "FirstFragment"

@AndroidEntryPoint
class FirstFragment : Fragment() {

    companion object {
        fun newInstance() = RecentMoviesFragment()
    }

    private val recentViewModel by viewModels<RecentListViewModel>()
    private lateinit var bindingHome: HomeBinding
    val recent by lazy { recentViewModel.state.value }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        bindingHome = HomeBinding.inflate(layoutInflater)
        bindingHome.recentMoviesRecyclerView.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        return bindingHome.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recentViewModel.state.observe(viewLifecycleOwner) {
            when {
                it.recent.isNotEmpty() -> {
                    bindingHome.recentMoviesRecyclerView.adapter = MainFragmentAdapter(it.recent)
                    Log.d(TAG, "The recent movies list: ${recent!!.recent}")
                }
                it.isLoading -> {
                    //show progress bar
                    Log.d(TAG, "Loading: ${recent?.isLoading}")
                }
                else -> {
                    //toast error message
                    Log.d(TAG, "Error:${recent?.error} ")
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        bindingHome.top250moviesIcon.setOnClickListener {
            val navController = findNavController()
            navController.navigate(R.id.top250MoviesFragment)
        }

        bindingHome.popularMoviesBackground.setOnClickListener {
            val navController = findNavController()
            navController.navigate(R.id.popularMoviesFragment)
        }
    }

    private inner class MainFragmentHolder(val binding: RecentRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var postion = 0
        fun bind(recent: ItemRecent, position: Int) {
            this.postion = postion
            binding.recentMoviesRv.load(recent.image)
            Log.d(TAG, "bind: ${recent.image}")

            binding.recentMoviesRv.setOnClickListener {
                findNavController().navigate(R.id.recentMoviesFragment)
            }
        }
    }

    private inner class MainFragmentAdapter(val recent: List<ItemRecent>) :
        RecyclerView.Adapter<MainFragmentHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainFragmentHolder {
            val binding = RecentRecyclerviewBinding.inflate(
                layoutInflater,
                parent,
                false
            )
            return MainFragmentHolder(binding)
        }

        override fun onBindViewHolder(holder: MainFragmentHolder, position: Int) {
            val movie = recent[position]
            holder.bind(movie, position)
        }

        override fun getItemCount(): Int = recent.size
    }
}
