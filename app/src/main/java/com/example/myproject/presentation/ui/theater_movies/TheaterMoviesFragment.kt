package com.example.myproject.presentation.ui.theater_movies

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import coil.load
import com.example.myproject.databinding.TheaterListItemBinding
import com.example.myproject.databinding.TheaterMoviesFragmentBinding
import com.example.myproject.domain.model.theater_movies.ItemTheater
import com.example.myproject.presentation.theater_list.TheaterListViewModel
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "TheaterMoviesFragment"

@AndroidEntryPoint
class TheaterMoviesFragment : Fragment() {

    companion object {
        fun newInstance() = TheaterMoviesFragment()
    }

    private val theaterViewModel by viewModels<TheaterListViewModel>()
    private lateinit var binding: TheaterMoviesFragmentBinding
    val state by lazy { theaterViewModel.state.value }

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
        theaterViewModel.state.observe(viewLifecycleOwner) {
            binding.moviesRv.adapter = TheaterAdapter(it)
            Log.d(TAG, "onViewCreated: $it")
        }
//        if (state.isNotBlank()){
//            Log.d(TAG, "onViewCreated: ${state.error}")
//        }
//        if (state.isLoading){
//            Log.d(TAG, "onViewCreated: ${state.isLoading}")
//        }
//        if (state.theaters.isNotEmpty()){
//            binding.moviesRv.adapter = TheaterAdapter(state.theaters)
//            Log.d(TAG, "onViewCreated: ${state}")
//            Log.d(TAG, "onViewCreated: ${state.theaters}")
//        }
    }

    private inner class TheaterHolder(val binding: TheaterListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(theater: ItemTheater) {
            binding.titleTv.text = theater.title
            binding.posterIv.load(theater.image)
            Log.d(TAG, "bind: ${theater.image}")
            binding.backgroundImageView.load(theater.image)
            binding.releaseStateTv.text = theater.show_time
        }
    }

    private inner class TheaterAdapter(val theater: List<ItemTheater>) :
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