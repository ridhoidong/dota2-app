package com.idong.dota2app.di

import com.idong.core.domain.usecase.HeroInteractor
import com.idong.core.domain.usecase.HeroUserCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

/**
 * Created by ridhopratama on 30,August,2021
 */

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppModule {

    @Binds
    @ViewModelScoped
    abstract fun provideHeroUseCase(heroInteractor: HeroInteractor): HeroUserCase
}