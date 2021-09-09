package com.idong.dota2app.di

import android.content.Context
import com.idong.dota2app.ui.menu.BottomNavigation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by ridhopratama on 31,August,2021
 */

@Module
@InstallIn(SingletonComponent::class)
class NavigationModule {

    @Singleton
    @Provides
    fun provideBottomNavigation(@ApplicationContext context: Context): BottomNavigation = BottomNavigation(context)

}