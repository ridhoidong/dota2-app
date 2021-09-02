package com.idong.core.domain.usecase

import com.idong.core.data.source.Resource
import com.idong.core.domain.model.Hero
import com.idong.core.domain.repository.IHeroRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by ridhopratama on 30,August,2021
 */
class HeroInteractor @Inject constructor(private val heroRepository: IHeroRepository) : HeroUserCase {
    override fun getAllHero(): Flow<Resource<List<Hero>>> = heroRepository.getAllHero()

    override fun getListFeaturedHero(): Flow<Resource<List<Hero>>> = heroRepository.getListFeaturedHero()

    override fun getListNewHero(): Flow<Resource<List<Hero>>> = heroRepository.getListNewHero()

    override fun getFavoriteHero(): Flow<List<Hero>> = heroRepository.getFavoriteHero()

    override fun setFavoriteHero(hero: Hero, state: Boolean) = heroRepository.setFavoriteHero(hero, state)

    override fun getFavoriteHeroById(id: Int): Flow<Hero> = heroRepository.getFavoriteHeroById(id)
}