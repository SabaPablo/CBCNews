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
import com.doce.cactus.saba.cbcnews.MainActivity
import com.doce.cactus.saba.cbcnews.R
import com.doce.cactus.saba.cbcnews.databinding.FragmentHomeBinding
import com.google.android.material.chip.Chip
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<HomeViewModel>()
    private var adapter: NewsAdapter? = null

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
        setUpAdapter()
        configureListeners()
        configureObservers()
        observeEvents()
    }

    private fun setUpAdapter() {
        adapter = NewsAdapter()
        binding.newsRv.adapter = adapter
        binding.newsRv.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun configureListeners() {

        // Pull To Refresh
        binding.swipeRl.setOnRefreshListener {
            viewModel.getNetworkNews()
        }
        // Chip listener
        binding.filterCg.setOnCheckedStateChangeListener { _, checkedIds ->
            viewModel.setFilter(checkedIds)
        }
    }

    private fun configureObservers() {
        viewModel.newsLiveData.observe(viewLifecycleOwner) { news ->
            firstConfigurationAdapterAndRecyclerView()
            adapter?.submitList(news)
            setConfigurationToShowNews()
        }

        viewModel.newsFiltered.observe(viewLifecycleOwner){
            adapter?.submitList(it)
            dataScenario()

        }

        viewModel.types.observe(viewLifecycleOwner){types ->
            binding.filterCg.removeAllViews()
            types.forEachIndexed { index, nameChip ->
                binding.filterCg.addView(newChip(index, nameChip))
            }
        }
    }

    private fun observeEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.events.collect { events ->
                    when (events) {
                        HomeEvents.ErrorNews -> showErrorGetNewsAndTryToGetCacheNews()
                        HomeEvents.ErrorDBNews -> {
                            noDataScenario()
                            Toast.makeText(requireContext(),getString(R.string.unknown_error),Toast.LENGTH_SHORT).show()
                        }
                        HomeEvents.EmptyDBNews -> noDataScenario()
                    }
                }
            }
        }
    }

    private fun showErrorGetNewsAndTryToGetCacheNews() {
        viewModel.getCacheNews()
        if((requireActivity() as MainActivity).isConnected)
            Toast.makeText(requireContext(),getString(R.string.error_msj_service),Toast.LENGTH_LONG).show()
        else
            Toast.makeText(requireContext(),getString(R.string.error_msj_internet),Toast.LENGTH_LONG).show()
    }

    private fun newChip(index: Int, nameChip: String): View {
        val chip = Chip(requireContext())
        with(chip) {
            id = index
            text = nameChip
            isCheckable = true
            isChecked = false
        }
        return chip
    }

    private fun setConfigurationToShowNews() {
        binding.filterTv.visibility= View.VISIBLE
        hideShimmer()
        binding.swipeRl.isRefreshing = false
        dataScenario()
    }

    private fun firstConfigurationAdapterAndRecyclerView() {
        adapter = NewsAdapter()
        binding.newsRv.adapter = adapter
        binding.newsRv.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun hideShimmer() {
        binding.shimmerViewContainer.stopShimmer()
        binding.shimmerViewContainer.visibility = View.GONE
    }

    private fun noDataScenario() {
        hideShimmer()
        binding.swipeRl.isRefreshing = false
        binding.noDataLl.visibility = View.VISIBLE
    }

    private fun dataScenario(){
        hideShimmer()
        binding.swipeRl.isRefreshing = false
        binding.noDataLl.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

