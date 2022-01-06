package com.example.myproject.presentation.ui.home_fragment

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
import com.example.myproject.databinding.ComingSoonRecycleviewBinding
import com.example.myproject.databinding.HomeBinding
import com.example.myproject.databinding.RecentRecyclerviewBinding
import com.example.myproject.domain.model.coming_soon_movies.ItemComingSoon
import com.example.myproject.domain.model.recent_movies.ItemRecent
import com.example.myproject.presentation.home_list.HomeListViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "HomeFragment"

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel by viewModels<HomeListViewModel>()
    private lateinit var bindingHome: HomeBinding

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
        bindingHome.cominSoonMoviesRecyclerView.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        return bindingHome.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.comingSoonHome.observe(viewLifecycleOwner) {
            when {
                it.comingSoon.isNotEmpty() -> {
                    bindingHome.shimmerViewContainer.visibility = View.INVISIBLE
//                    bindingHome.progressBar.visibility = View.INVISIBLE
                    bindingHome.cominSoonMoviesRecyclerView.adapter =
                        ComingSoonHomeAdapter(it.comingSoon)
                    Log.d(TAG, "The coming soon movies:${it!!.comingSoon} ")
                }
                it.isLoading -> {
                    //show progress bar
                    bindingHome.shimmerViewContainer.visibility = View.VISIBLE
//                    bindingHome.progressBar.visibility = View.VISIBLE
                    Log.d(TAG, "Loading: ${it?.isLoading}")
                }
                it.error.isNotEmpty() -> {
                    //toast error message
                    val snackBar =
                        Snackbar.make(requireView(), "Error Connection", Snackbar.LENGTH_LONG)
                    snackBar.setAction("Dismiss") { snackBar.dismiss() }
                    snackBar.show()
                    bindingHome.cominSoonMoviesRecyclerView.visibility = View.INVISIBLE
                    bindingHome.comingSoonMovies.visibility = View.INVISIBLE
                    bindingHome.errorMsgTv.visibility = View.VISIBLE
                    bindingHome.errorMsgTv.text = it?.error
                    Log.d(TAG, "Error 1: ${it?.error}")
                }
            }
        }
        homeViewModel.recentHome.observe(viewLifecycleOwner) {
            when {
                it.recent.isNotEmpty() -> {
                    bindingHome.recentMoviesRecyclerView.adapter = RecentHomeAdapter(it.recent)
                    Log.d(TAG, "The coming soon movies:${it!!.recent} ")
                }
                it.isLoading -> {
                    //show progress bar
                    Log.d(TAG, "Loading: ${it?.isLoading}")
                }
                it.error.isNotEmpty() -> {
                    //toast error message
                    val snackBar =
                        Snackbar.make(requireView(), "Error Connection", Snackbar.LENGTH_LONG)
                    snackBar.setAction("dismiss") { snackBar.dismiss() }
                    snackBar.show()
                    bindingHome.recentMoviesRecyclerView.visibility = View.INVISIBLE
                    bindingHome.recentMovies.visibility = View.INVISIBLE
                    bindingHome.errorMsgTv.visibility = View.VISIBLE
                    bindingHome.errorMsgTv.text = it?.error
                    Log.d(TAG, "The Error: ${it?.error}")
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        bindingHome.top250moviesBackground.setOnClickListener {
            val navController = findNavController()
            navController.navigate(R.id.top250MoviesFragment)
        }
        bindingHome.popularMoviesBackground.setOnClickListener {
            val navController = findNavController()
            navController.navigate(R.id.popularMoviesFragment)
        }
        bindingHome.top250TvsBackground.setOnClickListener {
            val navController = findNavController()
            navController.navigate(R.id.top250TVsFragment)
        }
    }

    private inner class RecentHomeHolder(val binding: RecentRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        //        var position = 0
        fun bind(recentHome: ItemRecent, position: Int) {
            Log.d(TAG, "bind: ${recentHome.image}")

            binding.recentMoviesMainRv.setOnClickListener {
                val position = bundleOf("position" to position)
                findNavController().navigate(R.id.recentMoviesFragment,position)
            }
        }
    }

    private inner class RecentHomeAdapter(val recentHome: List<ItemRecent>) :
        RecyclerView.Adapter<RecentHomeHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentHomeHolder {
            val binding = RecentRecyclerviewBinding.inflate(
                layoutInflater,
                parent,
                false
            )
            return RecentHomeHolder(binding)
        }

        override fun onBindViewHolder(holder: RecentHomeHolder, position: Int) {
            val movie = recentHome[position]
            holder.bind(movie, position)
        }

        override fun getItemCount(): Int = recentHome.size
    }

    //-----------------------ComingSoon Holder and adapter-------------------------//

    private inner class ComingSoonHomeHolder(val binding: ComingSoonRecycleviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var itemPosition: Int? = null
        fun bind(comingSoonHome: ItemComingSoon, position: Int) {
            itemPosition = position
            binding.comingSoonMoviesMainRv.load(comingSoonHome.image)
            Log.d(TAG, "bind: ${comingSoonHome.image}")

            binding.comingSoonMoviesMainRv.setOnClickListener {
                val position = bundleOf("position" to position)
                findNavController().navigate(R.id.comingSoonMoviesFragment, position)
            }
        }
    }

    private inner class ComingSoonHomeAdapter(val comingSoonHome: List<ItemComingSoon>) :
        RecyclerView.Adapter<ComingSoonHomeHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComingSoonHomeHolder {
            val binding = ComingSoonRecycleviewBinding.inflate(
                layoutInflater,
                parent,
                false
            )
            return ComingSoonHomeHolder(binding)
        }

        override fun onBindViewHolder(holder: ComingSoonHomeHolder, position: Int) {
            val comingSoonMovie = comingSoonHome[position]
            holder.bind(comingSoonMovie, position)
        }

        override fun getItemCount(): Int = comingSoonHome.size
    }
}
