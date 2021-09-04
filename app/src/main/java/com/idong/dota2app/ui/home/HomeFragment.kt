package com.idong.dota2app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.facebook.shimmer.ShimmerFrameLayout
import com.idong.core.data.source.Resource
import com.idong.core.domain.model.Hero
import com.idong.core.ui.HeroesAdapter
import com.idong.core.utils.Constant
import com.idong.core.utils.GridSpacingItemDecoration
import com.idong.dota2app.R
import com.idong.dota2app.databinding.FragmentHomeBinding
import com.idong.dota2app.ui.detail.DetailHeroActivity
import dagger.hilt.android.AndroidEntryPoint

    /**
 * Created by ridhopratama on 30,August,2021
 */

@AndroidEntryPoint
class HomeFragment : Fragment(){

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var adapterHero: HeroesAdapter
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
            loadHero()
            binding.btLoadAllHero.setOnClickListener {
                findNavController().navigate(R.id.listHeroActivity)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadHero() {

        homeViewModel.apply {
            allHero.observe(viewLifecycleOwner, Observer(this@HomeFragment::handleStateAllHero))
            featuredHero.observe(viewLifecycleOwner, Observer(this@HomeFragment::handleStateFeaturedHero))
            newHero.observe(viewLifecycleOwner, Observer(this@HomeFragment::handleStateNewHero))
        }

        adapterHero = HeroesAdapter().apply {
            onItemClick = { hero ->
                findNavController().navigate(
                    R.id.action_homeFragment_to_detailHeroActivity,
                    bundleOf(DetailHeroActivity.EXTRA_DATA to hero)
                )
            }
        }
        with(binding.rvAllHero) {
            layoutManager = GridLayoutManager(requireContext(), Constant.HEROES_GRID)
            adapter = adapterHero
            val spacing = resources.getDimensionPixelSize(R.dimen.margin_padding_small)
            addItemDecoration(GridSpacingItemDecoration(Constant.HEROES_GRID, spacing, false, 0))
        }

        adapterFeaturedHero = FeaturedHeroesHomeAdapter().apply {
            onItemClick = { hero ->
                findNavController().navigate(
                    R.id.action_homeFragment_to_detailHeroActivity,
                    bundleOf(DetailHeroActivity.EXTRA_DATA to hero)
                )
            }
        }
        with(binding.rvFeaturedHero) {
            layoutManager = LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.HORIZONTAL,
                    false
            )
            adapter = adapterFeaturedHero
        }

        adapterNewHero = FeaturedHeroesHomeAdapter()
        .apply {
            onItemClick = { hero ->
                findNavController().navigate(
                    R.id.action_homeFragment_to_detailHeroActivity,
                    bundleOf(DetailHeroActivity.EXTRA_DATA to hero)
                )
            }
        }
        with(binding.rvNewHero) {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = adapterNewHero
        }
    }

    private fun handleStateAllHero(viewState: Resource<List<Hero>>?) {
        viewState?.let { response ->
            when(response) {
                is Resource.Loading -> toggleLoading(true, binding.smAllHero)
                is Resource.Success -> {
                    toggleLoading(false, binding.smAllHero)
                    response.data?.let {
                        showData(getString(R.string.label_all_hero), it)
                    }
                }
                is Resource.Error -> {
                    toggleLoading(false, binding.smAllHero)
                    response.message?.let {
                        showError(getString(R.string.label_all_hero), it)
                    }
                }
            }
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
                        showError(getString(R.string.label_featured_hero), it)
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
                        showError(getString(R.string.label_new_hero), it)
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
            getString(R.string.label_all_hero) -> {
                binding.rvAllHero.visibility = View.VISIBLE
                val listData = (data as List<Hero>).shuffled().take(6)
                adapterHero.updateData(listData.toMutableList())
                if (listData.isNullOrEmpty()) {
                    binding.rvAllHero.visibility = View.GONE
                    binding.llEmptyAllHero.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun showError(type: String, error: String) {
        when(type) {
            getString(R.string.label_featured_hero) -> {
                binding.rvFeaturedHero.visibility = View.GONE
                binding.llEmptyFeaturedHero.visibility = View.VISIBLE
            }
            getString(R.string.label_new_hero) -> {
                binding.rvNewHero.visibility = View.GONE
                binding.llEmptyNewHero.visibility = View.VISIBLE
            }
            getString(R.string.label_all_hero) -> {
                binding.rvAllHero.visibility = View.GONE
                binding.llEmptyAllHero.visibility = View.VISIBLE
            }
        }
        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
    }
}