package com.idong.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.idong.core.domain.usecase.HeroUserCase
import javax.inject.Inject

/**
 * Created by ridhopratama on 04,September,2021
 */
class ViewModelFactory @Inject constructor(private val heroUserCase: HeroUserCase) :
    ViewModelProvider.NewInstanceFactory() {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            when {
                modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                    FavoriteViewModel(heroUserCase) as T
                }
                else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
            }

    }