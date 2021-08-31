package com.idong.core.domain.model

import android.os.Parcelable
import com.idong.core.data.source.local.entity.Ability
import kotlinx.parcelize.Parcelize

/**
 * Created by ridhopratama on 30,August,2021
 */
@Parcelize
data class Hero(
    val id: Int,
    val name: String,
    val agiBase: String,
    val agiGain: String,
    val armor: String,
    val attackType: String,
    val damage: String,
    val description: String,
    val intBase: String,
    val intGain: String,
    val localizedName: String,
    val movementSpeed: String,
    val strBase: String,
    val strGain: String,
    val tagline: String,
    val type: String,
    var isFavorite: Boolean = false,
    val abilities: List<Ability>
) : Parcelable
