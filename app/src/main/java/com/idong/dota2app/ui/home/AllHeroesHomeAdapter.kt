package com.idong.dota2app.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.idong.core.BuildConfig
import com.idong.core.domain.model.Hero
import com.idong.dota2app.R
import com.idong.dota2app.databinding.ItemAllHeroesHomeBinding
import com.idong.dota2app.databinding.ItemFeaturedHeroesHomeBinding
import com.idong.dota2app.enum.HeroType
import com.idong.dota2app.enum.HeroTypeAttack

/**
 * Created by ridhopratama on 30,August,2021
 */
class AllHeroesHomeAdapter(): RecyclerView.Adapter<AllHeroesHomeAdapter.ViewHolder>() {
    private val listHeroes = mutableListOf<Hero>()
    var onItemClick: ((Hero) -> Unit)? = null

    fun updateData(newHeroes: MutableList<Hero>) {
        listHeroes.clear()
        listHeroes.addAll(newHeroes)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemAllHeroesHomeBinding = ItemAllHeroesHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemAllHeroesHomeBinding)
    }

    override fun getItemCount(): Int = listHeroes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listHeroes[position]
        holder.bind(data)
    }

    inner class ViewHolder(private val binding: ItemAllHeroesHomeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(hero: Hero) {
            with(binding) {
                val fixHeroName = hero.name.replace("npc_dota_hero_", "")
                Glide.with(itemView.context)
                    .load(BuildConfig.CDN_HERO_FACE + fixHeroName + ".png")
                    .apply(RequestOptions.placeholderOf(R.drawable.img_loading)
                        .error(R.drawable.img_logo_dota2))
                    .into(ivHero)
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listHeroes[adapterPosition])
            }
        }
    }
}