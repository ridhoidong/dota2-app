package com.idong.dota2app.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.idong.core.domain.usecase.HeroUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(heroUserCase: HeroUserCase) : ViewModel() {

    val allHero = heroUserCase.getAllHero().asLiveData()
    val featuredHero = heroUserCase.getListFeaturedHero().asLiveData()
    val newHero = heroUserCase.getListNewHero().asLiveData()
}