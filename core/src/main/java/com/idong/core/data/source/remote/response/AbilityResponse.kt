package com.idong.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class AbilityResponse(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("localized_name")
    val localizedName: String,

    @field:SerializedName("name")
    val name: String
)