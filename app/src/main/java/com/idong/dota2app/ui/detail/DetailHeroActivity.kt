package com.idong.dota2app.ui.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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
import com.idong.core.utils.ViewUtil
import com.idong.core.utils.viewBinding
import com.idong.dota2app.R
import com.idong.dota2app.databinding.ActivityDetailHeroBinding
import com.idong.dota2app.enum.HeroType
import com.idong.dota2app.enum.HeroTypeAttack
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs

/**
 * Created by ridhopratama on 02,September,2021
 */

@AndroidEntryPoint
class DetailHeroActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DATA = "extra_data"
    }

    private val detailHeroViewModel: DetailHeroViewModel by viewModels()
    private val binding by viewBinding(ActivityDetailHeroBinding::inflate)
    private lateinit var abilitiesHeroesAdapter: AbilitiesHeroesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar()
        val detailHero = intent.getParcelableExtra<Hero>(EXTRA_DATA)
        showDetailHero(detailHero)
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
                    abs(verticalOffset) > appBarLayout.totalScrollRange / 2 -> {
                        window.statusBarColor = ContextCompat.getColor(this@DetailHeroActivity, R.color.colorPrimaryDark)
                    }
                    else -> {
                        window.statusBarColor = ContextCompat.getColor(this@DetailHeroActivity, android.R.color.transparent)
                    }
                }
            })
        }
    }

    private fun showDetailHero(detailHero: Hero?) {
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
                    val spacing = resources.getDimensionPixelSize(R.dimen.margin_padding_small)
                    addItemDecoration(GridSpacingItemDecoration(4, spacing, false, 0))
                }
                abilitiesHeroesAdapter.updateData(detailHero.abilities.toMutableList())

                var statusFavorite = false
                detailHeroViewModel.getFavoriteHeroById(detailHero.id).observeOnce(this@DetailHeroActivity, {
                    statusFavorite = it
                    setStatusFavorite(statusFavorite)
                })

                binding.fabSave.setOnClickListener {
                    statusFavorite = !statusFavorite
                    detailHeroViewModel.setFavoriteHero(detailHero, statusFavorite)
                    setStatusFavorite(statusFavorite)
                    if (statusFavorite) {
                        ViewUtil.showNotification(this@DetailHeroActivity, binding.root, getString(R.string.label_add_favorite))
                    }
                    else {
                        ViewUtil.showNotification(this@DetailHeroActivity, binding.root, getString(R.string.label_delete_favorite))
                    }
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