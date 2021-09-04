package com.idong.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.idong.core.domain.usecase.HeroUserCase

class FavoriteViewModel (heroUserCase: HeroUserCase) : ViewModel() {

    val favoriteHero = heroUserCase.getFavoriteHero().asLiveData()

}