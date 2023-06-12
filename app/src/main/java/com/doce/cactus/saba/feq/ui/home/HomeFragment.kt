package com.doce.cactus.saba.feq.ui.home

import android.graphics.drawable.AnimationDrawable
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
import com.doce.cactus.saba.feq.MainActivity
import com.doce.cactus.saba.feq.R
import com.doce.cactus.saba.feq.databinding.FragmentHomeBinding
import com.doce.cactus.saba.feq.listener.OnIntClickListener
import com.google.android.material.chip.Chip
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<HomeViewModel>()

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

        val animationDrawable = binding.animatedBackground.background as AnimationDrawable
        animationDrawable.start()
        observeEvents()
        viewModel.getShows()
        val showAdapter = ShowListAdapter()
        binding.showsRv.layoutManager = LinearLayoutManager(requireContext())

        binding.showsRv.adapter = showAdapter


        binding.dayRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val items = listOf(1,2,3,4,5,6,7,8,9,10,11,12,13,14)
        val adapter = DayListAdapter(object : OnIntClickListener{
            override fun onItemClick(item: Int?) {
                item?.let {
                    viewModel.showEventsDay(it)
                }
            }

        })

        viewModel.showsFilteredByDay.observe(viewLifecycleOwner){
            showAdapter.submitList(it)
        }
        binding.dayRv.adapter = adapter

        adapter.submitList(items)





    }


    private fun observeEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.events.collect { events ->
                    when (events) {
                        HomeEvents.EmptyDBNews -> TODO()
                        HomeEvents.ErrorDBNews -> TODO()
                        HomeEvents.ErrorNews -> TODO()
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

