package com.idong.dota2app.ui.hero

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.navigation.ActivityNavigator
import androidx.recyclerview.widget.GridLayoutManager
import com.facebook.shimmer.ShimmerFrameLayout
import com.idong.core.data.source.Resource
import com.idong.core.domain.model.Hero
import com.idong.core.ui.HeroesAdapter
import com.idong.core.utils.Constant
import com.idong.core.utils.GridSpacingItemDecoration
import com.idong.dota2app.R
import com.idong.dota2app.databinding.ActivityListHeroBinding
import com.idong.dota2app.ui.detail.DetailHeroActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by ridhopratama on 04,September,2021
 */

@AndroidEntryPoint
class ListHeroActivity : AppCompatActivity() {

    private val listHeroViewModel: ListHeroViewModel by viewModels()
    private lateinit var binding: ActivityListHeroBinding
    private lateinit var adapterHero: HeroesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListHeroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupHeader()
        setupSearchView()
        loadHero()
    }

    private fun setupHeader() {
        with(binding.toolbar) {
            tvTitle.text = getString(R.string.label_choose_your_hero)
            btnBack.setOnClickListener {
                onBackPressed()
            }
        }
    }

    private fun setupSearchView() {
        with(binding.etSearchHero) {
            hint = context.getString(R.string.label_search_hero)
            doOnTextChanged { text, _, _, _ ->
                adapterHero.filter.filter(text)
            }
        }
    }

    private fun loadHero() {
        listHeroViewModel.apply {
            allHero.observe(this@ListHeroActivity, Observer(this@ListHeroActivity::handleStateAllHero))
        }

        adapterHero = HeroesAdapter().apply {
            onItemClick = { hero ->
                val destination = ActivityNavigator(this@ListHeroActivity)
                                    .createDestination()
                                    .setIntent(Intent(this@ListHeroActivity, DetailHeroActivity::class.java))
                ActivityNavigator(this@ListHeroActivity)
                        .navigate(
                                destination,
                                bundleOf(DetailHeroActivity.EXTRA_DATA to hero),
                                null,
                                null)
            }
        }
        with(binding.rvAllHero) {
            layoutManager = GridLayoutManager(this@ListHeroActivity, Constant.HEROES_GRID)
            adapter = adapterHero
            val spacing = resources.getDimensionPixelSize(R.dimen.margin_padding_small)
            addItemDecoration(GridSpacingItemDecoration(Constant.HEROES_GRID, spacing, false, 0))
        }
    }

    private fun handleStateAllHero(viewState: Resource<List<Hero>>?) {
        viewState?.let { response ->
            when(response) {
                is Resource.Loading -> toggleLoading(true, binding.smAllHero)
                is Resource.Success -> {
                    toggleLoading(false, binding.smAllHero)
                    response.data?.let {
                        showData(it)
                    }
                }
                is Resource.Error -> {
                    toggleLoading(false, binding.smAllHero)
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

    private fun showData(data: List<Hero>) {
        binding.rvAllHero.visibility = View.VISIBLE
        adapterHero.updateData(data.toMutableList())
        if (data.isNullOrEmpty()) {
            binding.rvAllHero.visibility = View.GONE
            binding.llEmptyAllHero.visibility = View.VISIBLE
        }
    }

    private fun showError(error: String) {
        binding.rvAllHero.visibility = View.GONE
        binding.llEmptyAllHero.visibility = View.VISIBLE
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

}