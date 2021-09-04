package com.idong.dota2app.di

import com.idong.core.domain.usecase.HeroInteractor
import com.idong.core.domain.usecase.HeroUserCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by ridhopratama on 30,August,2021
 */

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun provideHeroUseCase(heroInteractor: HeroInteractor): HeroUserCase
}