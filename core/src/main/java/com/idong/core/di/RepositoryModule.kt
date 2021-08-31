package com.idong.core.di

import com.idong.core.data.source.HeroRepository
import com.idong.core.domain.repository.IHeroRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by ridhopratama on 30,August,2021
 */

@Module(includes = [DatabaseModule::class, NetworkModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(heroRepository : HeroRepository) : IHeroRepository

}