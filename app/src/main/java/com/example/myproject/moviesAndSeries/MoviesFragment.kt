package com.example.myproject.moviesAndSeries

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.example.myproject.databinding.MoviesFragmentBinding
import com.example.myproject.movies.MoviesViewModel


class MoviesFragment : Fragment() {

    companion object {
        fun newInstance() = MoviesFragment()
    }

    private val movieViewModel: MoviesViewModel by lazy { ViewModelProvider(this)[MoviesViewModel::class.java] }
    private lateinit var binding: MoviesFragmentBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val snapHelper: SnapHelper = LinearSnapHelper()

        binding = MoviesFragmentBinding.inflate(layoutInflater)
        binding.moviesRv.layoutManager =
            LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )

        snapHelper.attachToRecyclerView(binding.moviesRv)

        return binding.root
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        movieViewModel.get.observe(
//            viewLifecycleOwner,
//            Observer {
//                binding.movieRv.adapter = MoviesAdapter(it)
//            }
//        )
//    }
//
//
}



