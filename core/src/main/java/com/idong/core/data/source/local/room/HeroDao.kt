package com.idong.core.data.source.local.room

import androidx.room.*
import com.idong.core.data.source.local.entity.HeroEntity
import kotlinx.coroutines.flow.Flow

/**
 * Created by ridhopratama on 29,August,2021
 */

@Dao
interface HeroDao {

    @Query("SELECT * FROM heroes")
    fun getAllHero(): Flow<List<HeroEntity>>

    @Query("SELECT * FROM heroes WHERE isFavorite = 1")
    fun getFavoriteHero(): Flow<List<HeroEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHero(heroEntity: List<HeroEntity>)

    @Update
    fun updateFavoriteHero(heroEntity: HeroEntity)
}