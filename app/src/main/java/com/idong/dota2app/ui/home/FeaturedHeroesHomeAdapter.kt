package com.idong.dota2app.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.idong.core.BuildConfig
import com.idong.core.domain.model.Hero
import com.idong.dota2app.R
import com.idong.dota2app.databinding.ItemFeaturedHeroesHomeBinding
import com.idong.dota2app.enum.HeroType
import com.idong.dota2app.enum.HeroTypeAttack
import java.util.*

/**
 * Created by ridhopratama on 30,August,2021
 */
class FeaturedHeroesHomeAdapter : RecyclerView.Adapter<FeaturedHeroesHomeAdapter.ViewHolder>() {
    private val listHeroes = mutableListOf<Hero>()
    var onItemClick: ((Hero) -> Unit)? = null

    fun updateData(newHeroes: MutableList<Hero>) {
        listHeroes.clear()
        listHeroes.addAll(newHeroes)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemFeaturedHeroesHomeBinding = ItemFeaturedHeroesHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemFeaturedHeroesHomeBinding)
    }

    override fun getItemCount(): Int = listHeroes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listHeroes[position]
        holder.bind(data)
    }

    inner class ViewHolder(private val binding: ItemFeaturedHeroesHomeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(hero: Hero) {
            with(binding) {
                val fixHeroName = hero.name.replace("npc_dota_hero_", "")
                Glide.with(itemView.context)
                    .load(BuildConfig.CDN_HERO_FULL + fixHeroName + ".png")
                    .apply(RequestOptions.placeholderOf(R.drawable.img_loading)
                        .error(R.drawable.img_logo_dota2))
                    .into(ivHero)

                tvName.text = hero.localizedName
                tvTagline.text = hero.tagline
                when (hero.attackType.toUpperCase(Locale.ROOT)) {
                    HeroTypeAttack.RANGED.name -> ivTypeAttack.setImageResource(R.drawable.ic_ranged)
                    else -> ivTypeAttack.setImageResource(R.drawable.ic_melee)
                }
                tvTypeAttack.text = hero.attackType.capitalize(Locale.ROOT)
                when(hero.type.toUpperCase(Locale.ROOT)) {
                    HeroType.STRENGTH.name -> ivType.setImageResource(R.drawable.img_strength)
                    HeroType.AGILITY.name -> ivType.setImageResource(R.drawable.img_agility)
                    else -> ivType.setImageResource(R.drawable.img_intelligence)
                }
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listHeroes[adapterPosition])
            }
        }
    }
}