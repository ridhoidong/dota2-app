package com.idong.core.data.source.remote.response

import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Created by ridhopratama on 29,August,2021
 */

data class ListHeroResponse(
    @field:SerializedName("success")
    val success: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("data")
    val data: List<HeroResponse>
)
