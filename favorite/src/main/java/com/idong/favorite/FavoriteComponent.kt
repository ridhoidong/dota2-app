package com.idong.favorite

import android.content.Context
import com.idong.dota2app.di.FavoriteModuleDependencies
import dagger.BindsInstance
import dagger.Component

/**
 * Created by ridhopratama on 04,September,2021
 */

@Component(dependencies = [FavoriteModuleDependencies::class])
interface FavoriteComponent {

    fun inject(fragment: FavoriteFragment)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(favoriteModuleDependencies: FavoriteModuleDependencies): Builder
        fun build(): FavoriteComponent
    }

}