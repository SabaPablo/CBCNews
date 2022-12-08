package com.doce.cactus.saba.cbcnews.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.doce.cactus.saba.cbcnews.MainActivity
import com.doce.cactus.saba.cbcnews.R
import com.doce.cactus.saba.cbcnews.databinding.FragmentHomeBinding
import kotlinx.coroutines.delay
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

        viewModel.getNetworkNews()

        pullToRefresh()

        configureObservers()
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
        }
        binding.filterCg.setOnCheckedStateChangeListener { _, checkedIds ->
            viewModel.setFilter(checkedIds)
        }
        viewModel.newsFiltered.observe(viewLifecycleOwner){
            adapter?.submitList(it)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

