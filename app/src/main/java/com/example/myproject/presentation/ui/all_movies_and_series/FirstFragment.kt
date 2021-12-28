package com.example.myproject.presentation.ui.all_movies_and_series

import android.app.ActionBar
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.myproject.R
import com.example.myproject.databinding.HomeBinding
import com.example.myproject.databinding.RecentRecyclerviewBinding
import com.example.myproject.domain.model.recent_movies.ItemRecent
import com.example.myproject.presentation.recent_list.RecentListViewModel
import com.example.myproject.presentation.top250_list.Top250ListViewModel
import com.example.myproject.presentation.ui.recent_movies.RecentMoviesFragment
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "FirstFragment"

@AndroidEntryPoint
class FirstFragment : Fragment() {

    companion object {
        fun newInstance() = RecentMoviesFragment()
    }

    private val recentViewModel by viewModels<RecentListViewModel>()
    private lateinit var binding: HomeBinding
    val recent by lazy { recentViewModel.state.value }

    private val top250MoviesViewModel by viewModels<Top250ListViewModel>()
    val top250 by lazy { top250MoviesViewModel.state.value }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = HomeBinding.inflate(layoutInflater)
        binding.recentMoviesRecyclerView.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recentViewModel.state.observe(viewLifecycleOwner) {
            binding.recentMoviesRecyclerView.adapter = MainFragmentAdapter(it.recent)

        }
    }

    override fun onStart() {
        super.onStart()
        binding.top250moviesIcon.setOnClickListener {
            val navController = findNavController()
            navController.navigate(R.id.top250MoviesFragment)
//            Toast.makeText(requireContext(), "az", Toast.LENGTH_SHORT).show()
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
            val binding =RecentRecyclerviewBinding.inflate(
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
