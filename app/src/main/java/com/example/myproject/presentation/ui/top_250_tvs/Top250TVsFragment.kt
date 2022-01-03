package com.example.myproject.presentation.ui.top_250_tvs

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.myproject.databinding.Top250TvsFragmentBinding
import com.example.myproject.databinding.Top250TvsListItemBinding
import com.example.myproject.domain.model.top250_tvs.ItemTop250TVs
import com.example.myproject.presentation.top250_tvs_list.Top250TVsListViewModel
import com.google.android.material.snackbar.Snackbar
import com.jackandphantom.carouselrecyclerview.CarouselLayoutManager
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "Top250TVsFragment"

@AndroidEntryPoint
class Top250TVsFragment : Fragment() {

    companion object {
        fun newInstance() = Top250TVsFragment()
    }

    private val top250TVsViewModel by viewModels<Top250TVsListViewModel>()
    private lateinit var binding: Top250TvsFragmentBinding
    val state by lazy { top250TVsViewModel.state.value }
    private var series: List<ItemTop250TVs>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = Top250TvsFragmentBinding.inflate(layoutInflater)
        binding.top250tvsRv.layoutManager =
            LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        setRecyclerView()
        return binding.root
    }

    private fun setRecyclerView() {
        binding.top250tvsRv.set3DItem(true)
        binding.top250tvsRv.setFlat(false)
        binding.top250tvsRv.setInfinite(false)
        binding.top250tvsRv.setIntervalRatio(0.4f)
        binding.top250tvsRv.setAlpha(false)
        binding.top250tvsRv.setItemSelectListener(object : CarouselLayoutManager.OnSelected {
            override fun onItemSelected(position: Int) {
                series?.let {
                    binding.top250tvsTitle.text = it[position].title
                    binding.top250tvsTitle2.text = it[position].title
                    Log.d(TAG, "Top 250 TVs titles: ${it.size}")
                }
            }
        })
        binding.top250tvsRv.layoutManager = binding.top250tvsRv.getCarouselLayoutManager()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        top250TVsViewModel.state.observe(viewLifecycleOwner) {
            when {
                it.top250TVs.isNotEmpty() -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    binding.top250tvsRv.adapter = Top250TVsAdapter(it.top250TVs)
                    series = it.top250TVs
                    Log.d(TAG, "The popular movies list: ${state!!.top250TVs}")
                }
                it.isLoading -> {
                    //show progress bar
                    binding.progressBar.visibility = View.VISIBLE
                    Log.d(TAG, "Loading: ${state?.isLoading}")
                }
                it.error.isNotBlank() -> {
                    //toast error message
                    val snackbar =
                        Snackbar.make(requireView(), "Error Connection", Snackbar.LENGTH_LONG)
                    snackbar.setAction("Dismiss") { snackbar.dismiss() }
                    snackbar.show()
                    Log.d(TAG, "Error: ${state?.error}")
                }
//                else -> {
//                    val snackbar =
//                        Snackbar.make(requireView(), "Unknown Error", Snackbar.LENGTH_LONG)
//                    snackbar.setAction("Dismiss") { snackbar.dismiss() }
//                    snackbar.show()
//                    Log.d(TAG, "Unknown error ${state?.error}")}
            }
            series?.let {
                binding.top250tvsTitle.text = it[0].title
                binding.top250tvsTitle2.text = it[0].title
                Log.d(TAG, "First series: $it")
            }
        }
    }

    private inner class Top250TVsHolder(val binding: Top250TvsListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(series: ItemTop250TVs) {
            binding.posterIv.load(series.image)
            binding.rate.text = series.rating
        }
    }

    private inner class Top250TVsAdapter(val series: List<ItemTop250TVs>) :
        RecyclerView.Adapter<Top250TVsHolder>() {
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): Top250TVsHolder {
            val binding = Top250TvsListItemBinding.inflate(
                layoutInflater,
                parent,
                false
            )
            return Top250TVsHolder(binding)
        }

        override fun onBindViewHolder(holder: Top250TVsHolder, position: Int) {
            val series = series[position]
            holder.bind(series)
        }

        override fun getItemCount(): Int = series.size
    }
}