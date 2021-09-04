package com.idong.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.idong.core.BuildConfig
import com.idong.core.R
import com.idong.core.databinding.ItemAllHeroesHomeBinding
import com.idong.core.domain.model.Hero

/**
 * Created by ridhopratama on 30,August,2021
 */
class HeroesAdapter(): RecyclerView.Adapter<HeroesAdapter.ViewHolder>(), Filterable {

    private val listHeroes = mutableListOf<Hero>()
    private var listHeroesFiltered = listHeroes
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

    override fun getItemCount(): Int = listHeroesFiltered.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listHeroesFiltered[position]
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
                onItemClick?.invoke(listHeroesFiltered[adapterPosition])
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val char = charSequence.toString()
                listHeroesFiltered = if (char.isEmpty()) {
                    listHeroes
                } else {
                    val filteredList = ArrayList<Hero>()
                    for (row: Hero in listHeroes) {
                        if (row.localizedName.toLowerCase().contains(char.toLowerCase()) or
                                row.attackType.toLowerCase().contains(char.toLowerCase()) or
                                row.type.toLowerCase().contains(char.toLowerCase())) {
                            filteredList.add(row)
                        }
                    }
                    filteredList
                }

                val filterResults = FilterResults()
                filterResults.values = listHeroesFiltered
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence?, results: FilterResults?) {
                listHeroesFiltered = results?.values as ArrayList<Hero>
                notifyDataSetChanged()
            }

        }
    }
}