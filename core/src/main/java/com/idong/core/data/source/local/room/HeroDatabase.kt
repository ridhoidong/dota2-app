package com.idong.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.idong.core.data.source.local.entity.HeroEntity

/**
 * Created by ridhopratama on 29,August,2021
 */

@Database(entities = [HeroEntity::class], version = 1, exportSchema = false)
@TypeConverters(RoomConverter::class)
abstract class HeroDatabase : RoomDatabase() {

    abstract fun heroDao(): HeroDao
}