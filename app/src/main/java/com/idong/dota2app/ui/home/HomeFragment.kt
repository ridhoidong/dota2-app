package com.idong.dota2app.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.application.idong.mypdam.models.ListMenu
import com.facebook.shimmer.ShimmerFrameLayout
import com.idong.core.data.source.Resource
import com.idong.core.domain.model.Hero
import com.idong.dota2app.R
import com.idong.dota2app.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

    /**
 * Created by ridhopratama on 30,August,2021
 */

@AndroidEntryPoint
class HomeFragment : Fragment(){

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var adapterFeaturedHero: FeaturedHeroesHomeAdapter
    private lateinit var adapterNewHero: FeaturedHeroesHomeAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            loadFeaturedHeroAndNewHero()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadFeaturedHeroAndNewHero() {

        homeViewModel.apply {
            featuredHero.observe(viewLifecycleOwner, Observer(this@HomeFragment::handleStateFeaturedHero))
            newHero.observe(viewLifecycleOwner, Observer(this@HomeFragment::handleStateNewHero))
        }

        adapterFeaturedHero = FeaturedHeroesHomeAdapter()
        with(binding.rvFeaturedHero) {
            layoutManager = LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.HORIZONTAL,
                    false
            )
            adapter = adapterFeaturedHero
        }

        adapterNewHero = FeaturedHeroesHomeAdapter()
        with(binding.rvNewHero) {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = adapterNewHero
        }
    }

    private fun handleStateFeaturedHero(viewState: Resource<List<Hero>>?) {
        viewState?.let { response ->
            when(response) {
                is Resource.Loading -> toggleLoading(true, binding.smFeaturedHero)
                is Resource.Success -> {
                    toggleLoading(false, binding.smFeaturedHero)
                    response.data?.let {
                        showData(getString(R.string.label_featured_hero), it)
                    }
                }
                is Resource.Error -> {
                    toggleLoading(false, binding.smFeaturedHero)
                    response.message?.let {
                        showError(it)
                    }
                }
            }
        }
    }


    private fun handleStateNewHero(viewState: Resource<List<Hero>>?) {
        viewState?.let { response ->
            when(response) {
                is Resource.Loading -> toggleLoading(true, binding.smNewHero)
                is Resource.Success -> {
                    toggleLoading(false, binding.smNewHero)
                    response.data?.let {
                        showData(getString(R.string.label_new_hero), it)
                    }
                }
                is Resource.Error -> {
                    toggleLoading(false, binding.smNewHero)
                    response.message?.let {
                        showError(it)
                    }
                }
            }
        }
    }

    private fun  toggleLoading(loading: Boolean, shimmerFrameLayout: ShimmerFrameLayout) {
        if (loading) {
            shimmerFrameLayout.startShimmer()
            shimmerFrameLayout.visibility = View.VISIBLE
        }
        else {
            shimmerFrameLayout.stopShimmer()
            shimmerFrameLayout.visibility = View.GONE
        }
    }

    private fun <T : Any> showData(type: String, data: List<T>) {
        when(type) {
            getString(R.string.label_featured_hero) -> {
                binding.rvFeaturedHero.visibility = View.VISIBLE
                val listData = data as List<Hero>
                adapterFeaturedHero.updateData(listData.toMutableList())
                if (listData.isNullOrEmpty()) {
                    binding.rvFeaturedHero.visibility = View.GONE
                    binding.llEmptyFeaturedHero.visibility = View.VISIBLE
                }
            }
            getString(R.string.label_new_hero) -> {
                binding.rvNewHero.visibility = View.VISIBLE
                val listData = data as List<Hero>
                adapterNewHero.updateData(listData.toMutableList())
                if (listData.isNullOrEmpty()) {
                    binding.rvNewHero.visibility = View.GONE
                    binding.llEmptyNewHero.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun showError(error: String) {
        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT)
    }
}