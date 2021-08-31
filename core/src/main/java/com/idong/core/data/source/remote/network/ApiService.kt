package com.idong.core.data.source.remote.network

import com.idong.core.data.source.remote.response.ListHeroResponse
import retrofit2.http.GET

/**
 * Created by ridhopratama on 30,August,2021
 */
interface ApiService {

    @GET("list-hero")
    suspend fun getListHero(): ListHeroResponse

    @GET("top-5-favorite-hero")
    suspend fun getListFeaturedHero(): ListHeroResponse

    @GET("top-5-new-hero")
    suspend fun getListNewHero(): ListHeroResponse
}