package com.example.myproject.ui.theater_movies

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import coil.load
import com.example.myproject.databinding.TheaterListItemBinding
import com.example.myproject.databinding.TheaterMoviesFragmentBinding
import com.example.myproject.models.theater_movies.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

private const val TAG = "TheaterMoviesFragment"

class TheaterMoviesFragment : Fragment() {

    companion object {
        fun newInstance() = TheaterMoviesFragment()
    }

    private val theaterViewModel: TheaterMoviesViewModel by lazy { ViewModelProvider(this)[TheaterMoviesViewModel::class.java] }
    private lateinit var binding: TheaterMoviesFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val snapHelper: SnapHelper = LinearSnapHelper()

        binding = TheaterMoviesFragmentBinding.inflate(layoutInflater)
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
        lifecycleScope.launch(Dispatchers.Main) {
            theaterViewModel.getDataTheaters().collect {
                binding.moviesRv.adapter =TheaterAdapter(it)
            }
        }
    }

    private inner class TheaterHolder(val binding: TheaterListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(theater: Item) {
            if (theater.imDbRating.isEmpty()) {
                binding.ratingBar.visibility = View.GONE
            } else {
                binding.ratingBar.rating = theater.imDbRating.toFloat()
            }
            binding.titleTv.text = theater.fullTitle
            binding.yearTv.text = theater.year
            binding.posterIv.load(theater.image)
            Log.d(TAG, "bind: ${theater.image}")
            binding.backgroundImageView.load(theater.image)
            binding.genresTv.text = theater.genres
            binding.releaseStateTv.text = theater.releaseState
            binding.runtimeStrTv.text = theater.runtimeMins
        }
    }

    private inner class TheaterAdapter(val theater: List<Item>) :
        RecyclerView.Adapter<TheaterHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TheaterHolder {
            val binding = TheaterListItemBinding.inflate(
                layoutInflater,
                parent,
                false
            )
            return TheaterHolder(binding)
        }

        override fun onBindViewHolder(holder: TheaterHolder, position: Int) {
            val movie = theater[position]
            holder.bind(movie)
        }

        override fun getItemCount(): Int = theater.size
    }
}