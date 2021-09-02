package com.idong.core.domain.repository

import com.idong.core.data.source.Resource
import com.idong.core.domain.model.Hero
import kotlinx.coroutines.flow.Flow

/**
 * Created by ridhopratama on 30,August,2021
 */
interface IHeroRepository {

    fun getAllHero(): Flow<Resource<List<Hero>>>

    fun getListFeaturedHero(): Flow<Resource<List<Hero>>>

    fun getListNewHero(): Flow<Resource<List<Hero>>>

    fun getFavoriteHero(): Flow<List<Hero>>

    fun setFavoriteHero(hero: Hero, state: Boolean)

    fun getFavoriteHeroById(id: Int): Flow<Hero>

}