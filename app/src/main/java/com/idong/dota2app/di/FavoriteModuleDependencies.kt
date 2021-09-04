package com.idong.dota2app.di

import com.idong.core.domain.usecase.HeroUserCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by ridhopratama on 04,September,2021
 */

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoriteModuleDependencies {

    fun heroUseCase(): HeroUserCase
}