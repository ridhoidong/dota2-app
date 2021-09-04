package com.idong.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.idong.core.domain.model.Hero
import com.idong.core.ui.HeroesAdapter
import com.idong.core.utils.Constant
import com.idong.core.utils.GridSpacingItemDecoration
import com.idong.dota2app.R
import com.idong.dota2app.di.FavoriteModuleDependencies
import com.idong.dota2app.ui.detail.DetailHeroActivity
import com.idong.favorite.databinding.FragmentFavoriteBinding
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

/**
 * Created by ridhopratama on 04,September,2021
 */

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    @Inject
    lateinit var factory: ViewModelFactory
    private val favoriteViewModel: FavoriteViewModel by viewModels { factory }
    private lateinit var adapterFavoriteHero: HeroesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerFavoriteComponent.builder()
            .context(requireContext())
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    requireContext(),
                    FavoriteModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        loadFavoriteHero()
        setupSearchView()
    }

    override fun onResume() {
        super.onResume()
        adapterFavoriteHero.filter.filter(binding.etSearchHero.text)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupToolbar() {
        with(binding) {
            btnBack.visibility = View.GONE
            tvTitle.text = getString(R.string.title_favorite_heroes)
        }
    }

    private fun setupSearchView() {
        with(binding.etSearchHero) {
            hint = context.getString(R.string.label_search_hero)
            doOnTextChanged { text, _, _, _ ->
                if (adapterFavoriteHero == null) return@doOnTextChanged
                adapterFavoriteHero.filter.filter(text)
            }
        }
    }

    private fun loadFavoriteHero() {
        favoriteViewModel.apply {
            favoriteHero.observe(viewLifecycleOwner, Observer(this@FavoriteFragment::handleStateFavoriteHero))
        }

        adapterFavoriteHero = HeroesAdapter().apply {
            onItemClick = { hero ->
                findNavController().navigate(
                    R.id.action_favoriteFragment_to_detailHeroActivity,
                    bundleOf(DetailHeroActivity.EXTRA_DATA to hero)
                )
            }
        }
        with(binding.rvAllHero) {
            layoutManager = GridLayoutManager(requireContext(), Constant.HEROES_GRID)
            adapter = adapterFavoriteHero
            val spacing = resources.getDimensionPixelSize(R.dimen.margin_padding_small)
            addItemDecoration(GridSpacingItemDecoration(Constant.HEROES_GRID, spacing, false, 0))
        }
    }

    private fun handleStateFavoriteHero(data: List<Hero>) {
        binding.rvAllHero.visibility = View.VISIBLE
        adapterFavoriteHero.updateData(data.toMutableList())
        if (data.isNullOrEmpty()) {
            binding.rvAllHero.visibility = View.GONE
            binding.llEmptyAllHero.visibility = View.VISIBLE
        }
    }
}