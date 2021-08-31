package com.idong.core.data.source.local.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ability(
    val id: Int,
    val description: String,
    val localizedName: String,
    val name: String
) : Parcelable