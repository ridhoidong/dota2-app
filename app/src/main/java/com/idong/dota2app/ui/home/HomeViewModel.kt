package com.idong.dota2app.ui.home

import androidx.lifecycle.*
import com.idong.core.data.source.Resource
import com.idong.core.domain.model.Hero
import com.idong.core.domain.usecase.HeroUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(heroUserCase: HeroUserCase) : ViewModel() {

    val allHero = heroUserCase.getAllHero().asLiveData()
    val featuredHero = heroUserCase.getListFeaturedHero().asLiveData()
    val newHero = heroUserCase.getListNewHero().asLiveData()
}