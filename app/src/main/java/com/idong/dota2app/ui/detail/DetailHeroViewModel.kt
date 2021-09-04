package com.idong.dota2app.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.idong.core.domain.model.Hero
import com.idong.core.domain.usecase.HeroUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by ridhopratama on 02,September,2021
 */

@HiltViewModel
class DetailHeroViewModel @Inject constructor(private val heroUserCase: HeroUserCase) : ViewModel() {

    fun setFavoriteHero(hero: Hero, newStatus: Boolean) = heroUserCase.setFavoriteHero(hero, newStatus)

    fun getFavoriteHeroById(id: Int) = heroUserCase.checkIsFavoriteById(id).asLiveData()

}