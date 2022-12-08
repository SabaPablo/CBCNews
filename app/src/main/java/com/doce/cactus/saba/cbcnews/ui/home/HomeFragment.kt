package com.doce.cactus.saba.cbcnews.ui.home

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.doce.cactus.saba.cbcnews.MainActivity
import com.doce.cactus.saba.cbcnews.R
import com.doce.cactus.saba.cbcnews.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<HomeViewModel>()
    var adapter: NewsAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        viewModel.getNewsWith((requireActivity() as MainActivity).hasConnection)
        configureObservers()
    }

    private fun configureObservers() {
        viewModel.newsLiveData.observe(viewLifecycleOwner) { news ->
            adapter = NewsAdapter(news)
            binding.newsRv.adapter = adapter
            binding.newsRv.layoutManager = LinearLayoutManager(requireContext())
        }
        binding.filterCg.setOnCheckedStateChangeListener { _, checkedIds ->
            viewModel.setFilter(checkedIds)
            Log.d("chip", checkedIds.toString())
        }
        viewModel.newsFiltered.observe(viewLifecycleOwner){
            adapter?.filterList(it)

        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

