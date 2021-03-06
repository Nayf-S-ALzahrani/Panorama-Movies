package com.example.myproject.presentation.ui.coming_soon_movies

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import coil.load
import com.example.myproject.databinding.ComingSoonListItemBinding
import com.example.myproject.databinding.ComingSoonMoviesFragmentBinding
import com.example.myproject.domain.model.coming_soon_movies.ItemComingSoon
import com.example.myproject.presentation.coming_soon_list.ComingSoonListViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.myproject.R



private const val TAG = "ComingSoonMoviesFragment"

@AndroidEntryPoint
class ComingSoonMoviesFragment : Fragment() {

    private val comingSoonViewModel by viewModels<ComingSoonListViewModel>()
    private lateinit var binding: ComingSoonMoviesFragmentBinding
    val state by lazy { comingSoonViewModel.state.value }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "position: ${comingSoonViewModel.position}")
        val snapHelper: SnapHelper = LinearSnapHelper()
        binding = ComingSoonMoviesFragmentBinding.inflate(layoutInflater)
        val layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        layoutManager.scrollToPosition(comingSoonViewModel.position)
        binding.comingSoonMoviesRv.layoutManager = layoutManager
        snapHelper.attachToRecyclerView(binding.comingSoonMoviesRv)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        comingSoonViewModel.state.observe(viewLifecycleOwner) {
            when {
                it.comingSoon.isNotEmpty() -> {
                    binding.progressBar.visibility = View.GONE
                    binding.comingSoonMoviesRv.adapter = ComingSoonAdapter(it.comingSoon)
                    Log.d(TAG, "The coming soon movies:${state?.comingSoon} ")
                }
                it.isLoading -> {
                    //show progress bar
                    binding.progressBar.visibility = View.VISIBLE
                    Log.d(TAG, "Loading:${state?.isLoading}")
                }
                else -> {
                    //toast error message
                    val snackbar =
                        Snackbar.make(requireView(), getString(R.string.error_conection), Snackbar.LENGTH_LONG)
                    snackbar.setAction(getString(R.string.dismiss)) { snackbar.dismiss() }
                    snackbar.show()
                    Log.d(TAG, "Error: ${state?.error}")
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        comingSoonViewModel.position = binding.comingSoonMoviesRv.getChildAdapterPosition(binding.comingSoonMoviesRv.focusedChild)
    }

    private inner class ComingSoonHolder(val binding: ComingSoonListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(comingSoon: ItemComingSoon) {
            binding.titleTv.text = comingSoon.title
            binding.posterIv.load(comingSoon.image)
            Log.d(TAG, "bind: ${comingSoon.image}")
            binding.genresTv.text = comingSoon.genres
            binding.releaseStateTv.text = comingSoon.showTime
            binding.posterIv.setOnClickListener {
                val id = comingSoon.comingSoonID
                val showId = bundleOf("showId" to id)
                findNavController().navigate(R.id.lastFragment, showId)
            }
        }
    }

    private inner class ComingSoonAdapter(val comingSoon: List<ItemComingSoon>) :
        RecyclerView.Adapter<ComingSoonHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComingSoonHolder {
            val binding = ComingSoonListItemBinding.inflate(
                layoutInflater,
                parent,
                false
            )
            return ComingSoonHolder(binding)
        }

        override fun getItemCount(): Int = comingSoon.size

        override fun onBindViewHolder(holder: ComingSoonHolder, position: Int) {
            val movie = comingSoon[position]
            holder.bind(movie)
        }
    }
}