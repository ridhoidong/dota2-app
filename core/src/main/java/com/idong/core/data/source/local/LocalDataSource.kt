package com.idong.core.data.source.local

import com.idong.core.data.source.local.entity.HeroEntity
import com.idong.core.data.source.local.room.HeroDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by ridhopratama on 29,August,2021
 */

@Singleton
class LocalDataSource @Inject constructor(private val heroDao: HeroDao) {

    fun getAllHero(): Flow<List<HeroEntity>> = heroDao.getAllHero()

    fun getFavoriteHero(): Flow<List<HeroEntity>> = heroDao.getFavoriteHero()

    suspend fun insertHero(heroList: List<HeroEntity>) = heroDao.insertHero(heroList)

    fun setFavoriteHero(heroEntity: HeroEntity, newState: Boolean) {
        heroEntity.isFavorite = newState
        heroDao.updateFavoriteHero(heroEntity)
    }

    fun checkIsFavoriteById(id: Int): Flow<Boolean> = heroDao.checkIsFavoriteById(id)

}