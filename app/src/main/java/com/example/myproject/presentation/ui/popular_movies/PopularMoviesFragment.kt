package com.example.myproject.presentation.ui.popular_movies

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.myproject.databinding.PopularListItemBinding
import com.example.myproject.databinding.PopularMoviesFragmentBinding
import com.example.myproject.domain.model.most_popular_movies.ItemPopular
import com.example.myproject.presentation.popular_list.PopularListViewModel
import com.google.android.material.snackbar.Snackbar
import com.jackandphantom.carouselrecyclerview.CarouselLayoutManager
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "PopularMoviesFragment"

@AndroidEntryPoint
class PopularMoviesFragment : Fragment() {

    private val popularViewModel by viewModels<PopularListViewModel>()
    private lateinit var binding: PopularMoviesFragmentBinding
    val state by lazy { popularViewModel.state.value }
    private var movies: List<ItemPopular>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PopularMoviesFragmentBinding.inflate(layoutInflater)
        binding.popularRv.layoutManager =
            LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        setRecyclerView()
        return binding.root
    }

    private fun setRecyclerView() {
        binding.popularRv.set3DItem(true)
        binding.popularRv.setFlat(false)
        binding.popularRv.setInfinite(false)
        binding.popularRv.setIntervalRatio(0.4f)
        binding.popularRv.setAlpha(false)
        binding.popularRv.setItemSelectListener(object : CarouselLayoutManager.OnSelected {
            override fun onItemSelected(position: Int) {
                movies?.let {
                    binding.popularMovieTitle2.text = it[position].title
                    binding.popularMovieTitle.text = it[position].title
                    Log.d(TAG, "popular movies titles: ${it.size}")
                }
            }
        })
        binding.popularRv.layoutManager = binding.popularRv.getCarouselLayoutManager()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        popularViewModel.state.observe(viewLifecycleOwner) {
            when {
                it.popular.isNotEmpty() -> {
                    binding.popularRv.adapter = PopularAdapter(it.popular)
                    movies = it.popular
//                    Log.d(TAG, "The popular movies list: ${state!!.popular}")
                }
                it.isLoading -> {
                    //show progress bar
                    Log.d(TAG, "Loading: ${state?.isLoading}")
                }
                it.error.isNotBlank() -> {
                    val snackbar =
                        Snackbar.make(requireView(), "Error Connection", Snackbar.LENGTH_LONG)
                    snackbar.setAction("dismiss") { snackbar.dismiss() }
                    snackbar.show()
                    Log.d(TAG, "Error: ${state?.error}")
                }
//                else -> Log.d(TAG, "Unknown error ${state?.error}")
            }
            movies?.let {
                binding.popularMovieTitle.text = it[0].title
                binding.popularMovieTitle2.text=it[0].title
//                Log.d(TAG, "first movie: $it")
            }
        }
    }


    private inner class PopularHolder(val binding: PopularListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        lateinit var movie: ItemPopular
        fun bind(popular: ItemPopular) {

            if (binding.posterIv.toString().isBlank()) {
                binding.posterIv.visibility = ImageView.GONE
            } else {
                binding.posterIv.load(popular.image)
                binding.rate.text = popular.rating
//                if (binding.rate.text.isNotBlank()){
//
//                    binding.rate.visibility = TextView.VISIBLE
//                    binding.rating.visibility = ImageView.VISIBLE
//                    binding.imageView.visibility = ImageView.VISIBLE
//
//                }
            }

        }
    }

    private inner class PopularAdapter(val popular: List<ItemPopular>) :
        RecyclerView.Adapter<PopularHolder>() {
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): PopularHolder {
            val binding = PopularListItemBinding.inflate(
                layoutInflater,
                parent,
                false
            )
            return PopularHolder(binding)
        }

        override fun onBindViewHolder(holder: PopularHolder, position: Int) {
            val movie = popular[position]

            holder.bind(movie)

//            Log.d(TAG, "bind: ${movie.title} : ${movie.rating}")

        }

        override fun getItemCount(): Int = popular.size
    }
}