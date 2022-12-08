package com.doce.cactus.saba.cbcnews.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.doce.cactus.saba.cbcnews.R
import com.doce.cactus.saba.cbcnews.databinding.FragmentHomeBinding
import com.google.android.material.chip.Chip
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<HomeViewModel>()
    var adapter: NewsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.shimmerViewContainer.startShimmer()
        viewModel.getNetworkNews()
        pullToRefresh()
        configureObservers()
        observeEvents()
    }

    private fun pullToRefresh() {

        binding.swipeRl.setOnRefreshListener {
            viewModel.getNetworkNews()
        }
    }

    private fun configureObservers() {
        viewModel.newsLiveData.observe(viewLifecycleOwner) { news ->
            binding.swipeRl.isRefreshing = false
            adapter = NewsAdapter()
            binding.newsRv.adapter = adapter
            binding.newsRv.layoutManager = LinearLayoutManager(requireContext())
            adapter?.submitList(news)
            viewModel.setChips(news)
            binding.shimmerViewContainer.stopShimmer()
            binding.shimmerViewContainer.visibility = View.GONE
        }
        binding.filterCg.setOnCheckedStateChangeListener { _, checkedIds ->
            viewModel.setFilter(checkedIds)
        }
        viewModel.newsFiltered.observe(viewLifecycleOwner){
            adapter?.submitList(it)

        }

        viewModel.types.observe(viewLifecycleOwner){types ->
            binding.filterCg.removeAllViews()
            types.forEachIndexed { index, s ->
                val chip = Chip(requireContext())
                chip.id = index
                chip.text = s
                chip.isCheckable = true
                chip.isChecked = true
                binding.filterCg.addView(chip)

            }
            binding.filterCg
        }
    }

    private fun observeEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.events.collect { events ->
                    when (events) {
                        HomeEvents.ErrorNews -> {
                            Toast.makeText(requireContext(),"Error connection",Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

