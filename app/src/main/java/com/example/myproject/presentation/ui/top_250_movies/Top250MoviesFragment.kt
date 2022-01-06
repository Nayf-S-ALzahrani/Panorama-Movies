package com.example.myproject.presentation.ui.top_250_movies

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.myproject.R
import com.example.myproject.databinding.Top250ListItemBinding
import com.example.myproject.databinding.Top250MoviesFragmentBinding
import com.example.myproject.domain.model.top250_movies.ItemTop250
import com.example.myproject.presentation.top250_list.Top250ListViewModel
import com.google.android.material.snackbar.Snackbar
import com.jackandphantom.carouselrecyclerview.CarouselLayoutManager
import dagger.hilt.android.AndroidEntryPoint

const val TAG = "Adapter"

@AndroidEntryPoint
class Top250MoviesFragment : Fragment() {
    companion object {
        fun newInstance() = Top250MoviesFragment()
    }

    private val top250MoviesViewModel by viewModels<Top250ListViewModel>()
    private lateinit var binding: Top250MoviesFragmentBinding
    val state by lazy { top250MoviesViewModel.state.value }
    private var movies: List<ItemTop250>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = Top250MoviesFragmentBinding.inflate(layoutInflater)
        setRecyclerView()
        return binding.root
    }

    private fun setRecyclerView() {
        binding.top250MoviesRv.set3DItem(true)
        binding.top250MoviesRv.setFlat(false)
        binding.top250MoviesRv.setInfinite(true)
        binding.top250MoviesRv.setIntervalRatio(0.4f)
        binding.top250MoviesRv.setAlpha(false)

        binding.top250MoviesRv.setItemSelectListener(object : CarouselLayoutManager.OnSelected {
            override fun onItemSelected(position: Int) {
                movies?.let {
                    binding.movieTitle.text = it[position].title
                    Log.d(TAG, "onItemSelected: work")
                }
            }
        })
        binding.top250MoviesRv.layoutManager = binding.top250MoviesRv.getCarouselLayoutManager()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        top250MoviesViewModel.state.observe(viewLifecycleOwner) {
            when {
                it.top250.isNotEmpty() -> {
                    binding.animationView.visibility = View.INVISIBLE
                    binding.top250MoviesRv.adapter = Top250MoviesAdapter(it.top250)
                    movies = it.top250
                    Log.d(TAG, "The top 250 movies list: ${state!!.top250}")
                }
                it.isLoading -> {
                    //show progress bar
                    binding.animationView.visibility = View.VISIBLE
                    Log.d(TAG, "Loading: ${state?.isLoading}")
                }
                else -> {
                    //toast error message
                    val snackbar =
                        Snackbar.make(requireView(), "Error Connection", Snackbar.LENGTH_LONG)
                    snackbar.setAction("Dismiss") { snackbar.dismiss() }
                    snackbar.show()
                    Log.d(TAG, "Error:${state?.error} ")
                }
            }
            movies?.let {
                binding.movieTitle.text = it[0].title
                Log.d(TAG, "onItemSelected: work")
            }
        }
    }

    private inner class Top250MoviesHolder(val binding: Top250ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        lateinit var movie: ItemTop250
        fun bind(movie: ItemTop250) {
            this.movie = movie
            binding.posterIv.load(movie.image)
            binding.rate.text = movie.rating
            Log.d(TAG, "Bind rating: ${movie.rating}")
            Log.d(TAG, "Bind title: ${movie.title}")
            binding.posterIv.setOnClickListener {
                val id = movie.top250ID
                val showId = bundleOf("showId" to id)
                findNavController().navigate(R.id.lastFragment, showId)
            }
        }
    }

    private inner class Top250MoviesAdapter(val topMovie: List<ItemTop250>) :
        RecyclerView.Adapter<Top250MoviesHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Top250MoviesHolder {
            val binding = Top250ListItemBinding.inflate(
                layoutInflater,
                parent,
                false
            )
            return Top250MoviesHolder(binding)
        }

        override fun onBindViewHolder(holder: Top250MoviesHolder, position: Int) {
            val movie = topMovie[position]
            holder.bind(movie)
        }

        override fun getItemCount(): Int = topMovie.size
    }
}