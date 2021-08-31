package com.idong.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class HeroResponse(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("agi_base")
    val agiBase: String,

    @field:SerializedName("agi_gain")
    val agiGain: String,

    @field:SerializedName("armor")
    val armor: String,

    @field:SerializedName("attack_type")
    val attackType: String,

    @field:SerializedName("damage")
    val damage: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("int_base")
    val intBase: String,

    @field:SerializedName("int_gain")
    val intGain: String,

    @field:SerializedName("localized_name")
    val localizedName: String,

    @field:SerializedName("movement_speed")
    val movementSpeed: String,

    @field:SerializedName("str_base")
    val strBase: String,

    @field:SerializedName("str_gain")
    val strGain: String,

    @field:SerializedName("tagline")
    val tagline: String,

    @field:SerializedName("type")
    val type: String,

    @field:SerializedName("abilities")
    val abilities: List<AbilityResponse>,
)