package com.idong.dota2app.ui.detail

import android.os.Bundle
import android.os.PersistableBundle
import android.text.Html
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.appbar.AppBarLayout
import com.idong.core.BuildConfig
import com.idong.core.domain.model.Hero
import com.idong.core.utils.GeneralUtil.observeOnce
import com.idong.core.utils.GeneralUtil.toRound
import com.idong.core.utils.GeneralUtil.toSpanned
import com.idong.core.utils.GridSpacingItemDecoration
import com.idong.dota2app.R
import com.idong.dota2app.databinding.ActivityDetailHeroBinding
import com.idong.dota2app.enum.HeroType
import com.idong.dota2app.enum.HeroTypeAttack
import com.idong.dota2app.ui.home.AllHeroesHomeAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs

/**
 * Created by ridhopratama on 02,September,2021
 */

@AndroidEntryPoint
class DetailHeroActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DATA = "extra_data"
        const val NETWORK_REQUEST = "network_request"
    }

    private val detailHeroViewModel: DetailHeroViewModel by viewModels()
    private lateinit var binding: ActivityDetailHeroBinding
    private lateinit var abilitiesHeroesAdapter: AbilitiesHeroesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailHeroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolbar()
        val detailHero = intent.getParcelableExtra<Hero>(EXTRA_DATA)
        val networkRequest = intent.getBooleanExtra(NETWORK_REQUEST, false)
        showDetailHero(detailHero, networkRequest)
    }

    private fun setupToolbar() {
        with(binding.tbPartialCollapsing) {
            btnBack.setOnClickListener {
                onBackPressed()
            }
        }

        with(binding.ablDetailMovie) {
            addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
                when {
                    abs(verticalOffset) == appBarLayout.totalScrollRange -> {
                        window.statusBarColor = ContextCompat.getColor(this@DetailHeroActivity, R.color.colorPrimaryDark)
                    }
                    verticalOffset == 0 -> {
                        window.statusBarColor = ContextCompat.getColor(this@DetailHeroActivity, android.R.color.transparent)
                    }
                    else -> {
                        if (abs(verticalOffset) > appBarLayout.totalScrollRange / 2) {
                            window.statusBarColor = ContextCompat.getColor(this@DetailHeroActivity, R.color.colorPrimaryDark)
                            binding.fabSave.visibility = View.GONE
                        }
                        else {
                            window.statusBarColor = ContextCompat.getColor(this@DetailHeroActivity, android.R.color.transparent)
                            binding.fabSave.visibility = View.VISIBLE
                        }
                    }
                }
            })
        }
    }

    private fun showDetailHero(detailHero: Hero?, networkRequest: Boolean) {
        detailHero?.let {

            val fixHeroName = detailHero.name.replace("npc_dota_hero_", "")
            Glide.with(this)
                .load(BuildConfig.CDN_HERO_FULL + fixHeroName + ".png")
                .apply(RequestOptions.placeholderOf(R.drawable.img_loading)
                    .error(R.drawable.img_logo_dota2))
                .into(binding.ivHero)

            with(binding.contentDetail) {
                tvName.text = detailHero.localizedName
                tvTagline.text = detailHero.tagline
                tvStrength.text = detailHero.strBase + " +${detailHero.strGain.toRound(2)}"
                tvAgility.text = detailHero.agiBase + " +${detailHero.agiGain.toRound(2)}"
                tvIntelligence.text = detailHero.agiBase + " +${detailHero.intGain.toRound(2)}"
                tvDamage.text = detailHero.damage.toRound(1)
                tvArmor.text = detailHero.armor.toRound(2)
                tvSpeed.text = detailHero.movementSpeed
                tvDescription.text = detailHero.description.toSpanned()
                when(detailHero.attackType.toUpperCase()) {
                    HeroTypeAttack.RANGED.name -> ivTypeAttack.setImageResource(R.drawable.ic_ranged)
                    else -> ivTypeAttack.setImageResource(R.drawable.ic_melee)
                }
                tvTypeAttack.text = detailHero.attackType.toUpperCase()
                when(detailHero.type.toUpperCase()) {
                    HeroType.STRENGTH.name -> ivType.setImageResource(R.drawable.img_strength)
                    HeroType.AGILITY.name -> ivType.setImageResource(R.drawable.img_agility)
                    else -> ivType.setImageResource(R.drawable.img_intelligence)
                }
                tvType.text = detailHero.type.toUpperCase()

                abilitiesHeroesAdapter = AbilitiesHeroesAdapter().apply {
                    onItemClick = { ability ->
                        AbilityBottomSheet().apply {
                            setData(ability)
                        }.show(supportFragmentManager, AbilityBottomSheet.TAG_BS)
                    }
                }
                with(binding.contentDetail.rvAbilities) {
                    layoutManager = GridLayoutManager(this@DetailHeroActivity, 4)
                    adapter = abilitiesHeroesAdapter
                    val spacing = resources.getDimensionPixelSize(com.idong.dota2app.R.dimen.margin_padding_small)
                    addItemDecoration(GridSpacingItemDecoration(4, spacing, false, 0))
                }
                abilitiesHeroesAdapter.updateData(detailHero.abilities.toMutableList())

                var statusFavorite = detailHero.isFavorite
                setStatusFavorite(statusFavorite)

                if (networkRequest) {
                    detailHeroViewModel.getFavoriteHeroById(detailHero.id).observeOnce(this@DetailHeroActivity, {
                        statusFavorite = it.isFavorite
                        setStatusFavorite(statusFavorite)
                    })
                }

                binding.fabSave.setOnClickListener {
                    statusFavorite = !statusFavorite
                    detailHeroViewModel.setFavoriteHero(detailHero, statusFavorite)
                    setStatusFavorite(statusFavorite)
                }
            }
        }
    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        if (statusFavorite) {
            binding.fabSave.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_24dp_fill))
        } else {
            binding.fabSave.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_24dp))
        }
    }

}