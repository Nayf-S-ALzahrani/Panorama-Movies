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
import android.content.Intent.getIntent

import android.content.Intent
import android.content.Intent.getIntent
import androidx.appcompat.view.menu.MenuView
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.myproject.R
import com.example.myproject.common.Constants


private const val TAG = "ComingSoonMoviesFragment"

@AndroidEntryPoint
class ComingSoonMoviesFragment : Fragment() {
    // var position: Int? = null

    private val comingSoonViewModel by viewModels<ComingSoonListViewModel>()
    private lateinit var binding: ComingSoonMoviesFragmentBinding
    val state by lazy { comingSoonViewModel.state.value }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //    position = comingSoonViewModel.position
        //   position=arguments?.getInt(Constants.POSITION)
//            arguments?.getInt(Constants.POSITION).let {//just for rotation
//                comingSoonViewModel.putPosition(it!!)
//            }
//        position = comingSoonViewModel.getPosition()
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
                    binding.comingSoonMoviesRv.adapter = ComingSoonAdapter(it.comingSoon)
                    Log.d(TAG, "The coming soon movies:${state?.comingSoon} ")
                }
                it.isLoading -> {
                    //show progress bar
                    Log.d(TAG, "Loading:${state?.isLoading}")
                }
                else -> {
                    //toast error message
                    val snackbar =
                        Snackbar.make(requireView(), "Error Connection", Snackbar.LENGTH_LONG)
                    snackbar.setAction("Dismiss") { snackbar.dismiss() }
                    snackbar.show()
                    Log.d(TAG, "Error: ${state?.error}")
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        // save position before transl
        comingSoonViewModel.position = binding.comingSoonMoviesRv.getChildAdapterPosition(binding.comingSoonMoviesRv.focusedChild)
    }

    private inner class ComingSoonHolder(val binding: ComingSoonListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        //        ,View.OnClickListener {
//        lateinit var comingSoon: ItemComingSoon

        fun bind(comingSoon: ItemComingSoon) {
//            this.comingSoon = comingSoon
            binding.titleTv.text = comingSoon.title
            binding.posterIv.load(comingSoon.image)
            Log.d(TAG, "bind: ${comingSoon.image}")
//            binding.backgroundImageView.load(comingSoon.image)
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