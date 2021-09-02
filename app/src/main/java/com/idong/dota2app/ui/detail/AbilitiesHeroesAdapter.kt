package com.idong.dota2app.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.idong.core.BuildConfig
import com.idong.core.data.source.local.entity.Ability
import com.idong.dota2app.R
import com.idong.dota2app.databinding.ItemAbilityHeroBinding

/**
 * Created by ridhopratama on 02,September,2021
 */
class AbilitiesHeroesAdapter(): RecyclerView.Adapter<AbilitiesHeroesAdapter.ViewHolder>() {
    private val listAbilities = mutableListOf<Ability>()
    var onItemClick: ((Ability) -> Unit)? = null

    fun updateData(newAbilities: MutableList<Ability>) {
        listAbilities.clear()
        listAbilities.addAll(newAbilities)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemAbilityHeroBinding = ItemAbilityHeroBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemAbilityHeroBinding)
    }

    override fun getItemCount(): Int = listAbilities.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listAbilities[position]
        holder.bind(data)
    }

    inner class ViewHolder(private val binding: ItemAbilityHeroBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(ability: Ability) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(BuildConfig.CDN_HERO_ABILITY + ability.name + ".png")
                    .apply(RequestOptions.placeholderOf(R.drawable.img_loading)
                        .error(R.drawable.img_logo_dota2))
                    .into(ivAbility)
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listAbilities[adapterPosition])
            }
        }
    }
}