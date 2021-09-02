package com.idong.core.data.source

import com.idong.core.data.source.local.LocalDataSource
import com.idong.core.data.source.remote.RemoteDataSource
import com.idong.core.data.source.remote.network.ApiResponse
import com.idong.core.data.source.remote.response.HeroResponse
import com.idong.core.domain.model.Hero
import com.idong.core.domain.repository.IHeroRepository
import com.idong.core.utils.AppExecutors
import com.idong.core.utils.DataMapper
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by ridhopratama on 30,August,2021
 */

@Singleton
class HeroRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val  appExecutors: AppExecutors
) : IHeroRepository {

    override fun getAllHero(): Flow<Resource<List<Hero>>> =
        object : NetworkBoundResource<List<Hero>, List<HeroResponse>>(appExecutors) {
            override fun loadFromDB(): Flow<List<Hero>> {
                return localDataSource.getAllHero().map {
                    DataMapper.heroEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Hero>?): Boolean  = data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<HeroResponse>>> =
                remoteDataSource.getListHero()

            override suspend fun saveCallResult(data: List<HeroResponse>) {
                val heroList = DataMapper.hereResponseToEntities(data)
                localDataSource.insertHero(heroList)
            }
        }.asFlow()

    override fun getListFeaturedHero(): Flow<Resource<List<Hero>>> {
        val a = remoteDataSource.getListFeaturedHero()
        return flow {
            emit(Resource.Loading())
            when (val featuredHero = remoteDataSource.getListFeaturedHero().first()) {
                is ApiResponse.Success -> {
                    val listHero = DataMapper.heroResponseToDomain(featuredHero.data)
                    emit(Resource.Success(listHero))
                }
                is ApiResponse.Empty -> {
                    emit(Resource.Success(emptyList<Hero>()))
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error<List<Hero>>(featuredHero.errorMessage))
                }
            }
        }
    }

    override fun getListNewHero(): Flow<Resource<List<Hero>>> {
        return flow {
            emit(Resource.Loading())
            when (val featuredHero = remoteDataSource.getListNewHero().first()) {
                is ApiResponse.Success -> {
                    val listHero = DataMapper.heroResponseToDomain(featuredHero.data)
                    emit(Resource.Success(listHero))
                }
                is ApiResponse.Empty -> {
                    emit(Resource.Success(emptyList<Hero>()))
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error<List<Hero>>(featuredHero.errorMessage))
                }
            }
        }
    }

    override fun getFavoriteHero(): Flow<List<Hero>> {
        return localDataSource.getFavoriteHero().map {
            DataMapper.heroEntitiesToDomain(it)
        }
    }

    override fun setFavoriteHero(hero: Hero, state: Boolean) {
        val heroEntity = DataMapper.heroDomainToEntities(hero)
        appExecutors.diskIO().execute {
            localDataSource.setFavoriteHero(heroEntity, state)
        }
    }

    override fun getFavoriteHeroById(id: Int): Flow<Hero> {
        val hero = localDataSource.getFavoriteHeroById(id)
        return hero.map {
            DataMapper.heroEntitiesToDomainSingle(it)
        }
    }
}