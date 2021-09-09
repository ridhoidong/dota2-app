package com.idong.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "heroes")
data class HeroEntity(
    @PrimaryKey
    @NonNull
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "agiBase")
    val agiBase: String,

    @ColumnInfo(name = "agiGain")
    val agiGain: String,

    @ColumnInfo(name = "armor")
    val armor: String,

    @ColumnInfo(name = "attackType")
    val attackType: String,

    @ColumnInfo(name = "damage")
    val damage: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "intBase")
    val intBase: String,

    @ColumnInfo(name = "intGain")
    val intGain: String,

    @ColumnInfo(name = "localizedName")
    val localizedName: String,

    @ColumnInfo(name = "movementSpeed")
    val movementSpeed: String,

    @ColumnInfo(name = "strBase")
    val strBase: String,

    @ColumnInfo(name = "strGain")
    val strGain: String,

    @ColumnInfo(name = "tagline")
    val tagline: String,

    @ColumnInfo(name = "type")
    val type: String,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false,

    @ColumnInfo(name = "abilities")
    val abilities: List<Ability>
)