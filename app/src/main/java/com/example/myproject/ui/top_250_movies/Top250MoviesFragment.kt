package com.example.myproject.ui.top_250_movies

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.myproject.databinding.Top250MoviesFragmentBinding
import com.example.myproject.databinding.Top250MoviesListItemBinding
import com.example.myproject.models.top250movies.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

const val TAG = "Adapter"

class Top250MoviesFragment : Fragment() {
    companion object {
        fun newInstance() = Top250MoviesFragment()
    }

    private val top250MoviesViewModel: Top250MoviesViewModel by lazy { ViewModelProvider(this)[Top250MoviesViewModel::class.java] }
    private lateinit var binding: Top250MoviesFragmentBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = Top250MoviesFragmentBinding.inflate(layoutInflater)

        binding.top250MoviesRv.set3DItem(true)
        binding.top250MoviesRv.setFlat(false)
        binding.top250MoviesRv.setInfinite(true)
        binding.top250MoviesRv.setIntervalRatio(0.4f)
        binding.top250MoviesRv.setAlpha(false)
        binding.top250MoviesRv.layoutManager = binding.top250MoviesRv.getCarouselLayoutManager()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch(Dispatchers.Main) {
            top250MoviesViewModel.getTop250MoviesRepo().collect {
                binding.top250MoviesRv.adapter = Top250MoviesAdapter(it)
                Log.d(TAG, "work $it")
            }
        }
    }

    private inner class Top250MoviesHolder(val binding: Top250MoviesListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Item) {
            binding.posterIv.load(movie.image)
        }
    }

    private inner class Top250MoviesAdapter(val topMovie: List<Item>) :
        RecyclerView.Adapter<Top250MoviesHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Top250MoviesHolder {
            val binding = Top250MoviesListItemBinding.inflate(
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