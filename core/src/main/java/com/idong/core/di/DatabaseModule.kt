package com.idong.core.di

import android.content.Context
import androidx.room.Room
import com.idong.core.BuildConfig
import com.idong.core.data.source.local.room.HeroDao
import com.idong.core.data.source.local.room.HeroDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

/**
 * Created by ridhopratama on 30,August,2021
 */

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext contex: Context) : HeroDatabase {
        val passphrase: ByteArray = SQLiteDatabase.getBytes(BuildConfig.PASSPHRASE.toCharArray())
        val factory = SupportFactory(passphrase)

        return Room.databaseBuilder(
            contex, HeroDatabase::class.java, "Hero.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }

    @Provides
    fun provideTourismDao(database: HeroDatabase) : HeroDao = database.heroDao()

}