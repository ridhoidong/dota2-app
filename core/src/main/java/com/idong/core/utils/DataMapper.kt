package com.idong.core.utils

import com.idong.core.data.source.local.entity.Ability
import com.idong.core.data.source.local.entity.HeroEntity
import com.idong.core.data.source.remote.response.AbilityResponse
import com.idong.core.data.source.remote.response.HeroResponse
import com.idong.core.domain.model.Hero

object DataMapper {
    fun hereResponseToEntities(input: List<HeroResponse>): List<HeroEntity> {
        val heroList = ArrayList<HeroEntity>()
        input.map {
            val hero = HeroEntity(
                id = it.id,
                name = it.name,
                agiBase = it.agiBase,
                agiGain = it.agiGain,
                armor = it.armor,
                attackType = it.attackType,
                damage = it.damage,
                description = it.description,
                intBase = it.intBase,
                intGain = it.intGain,
                localizedName = it.localizedName,
                movementSpeed = it.movementSpeed,
                strBase = it.strBase,
                strGain = it.strGain,
                tagline = it.tagline,
                type = it.type,
                isFavorite = false,
                abilities = abilityResponseToEntity(it.abilities)
            )
            heroList.add(hero)
        }
        return heroList
    }

    fun heroEntitiesToDomain(input: List<HeroEntity>) : List<Hero> =
        input.map {
            Hero(
                id = it.id,
                name = it.name,
                agiBase = it.agiBase,
                agiGain = it.agiGain,
                armor = it.armor,
                attackType = it.attackType,
                damage = it.damage,
                description = it.description,
                intBase = it.intBase,
                intGain = it.intGain,
                localizedName = it.localizedName,
                movementSpeed = it.movementSpeed,
                strBase = it.strBase,
                strGain = it.strGain,
                tagline = it.tagline,
                type = it.type,
                isFavorite = it.isFavorite,
                abilities = it.abilities
            )
        }

    fun heroResponseToDomain(input: List<HeroResponse>) : List<Hero> {
        val heroList = ArrayList<Hero>()
        input.map {
            val hero = Hero(
                id = it.id,
                name = it.name,
                agiBase = it.agiBase,
                agiGain = it.agiGain,
                armor = it.armor,
                attackType = it.attackType,
                damage = it.damage,
                description = it.description,
                intBase = it.intBase,
                intGain = it.intGain,
                localizedName = it.localizedName,
                movementSpeed = it.movementSpeed,
                strBase = it.strBase,
                strGain = it.strGain,
                tagline = it.tagline,
                type = it.type,
                isFavorite = false,
                abilities = abilityResponseToEntity(it.abilities)
            )
            heroList.add(hero)
        }
        return heroList
    }

    fun heroDomainToEntities(input: Hero) : HeroEntity =
        HeroEntity(
            id = input.id,
            name = input.name,
            agiBase = input.agiBase,
            agiGain = input.agiGain,
            armor = input.armor,
            attackType = input.attackType,
            damage = input.damage,
            description = input.description,
            intBase = input.intBase,
            intGain = input.intGain,
            localizedName = input.localizedName,
            movementSpeed = input.movementSpeed,
            strBase = input.strBase,
            strGain = input.strGain,
            tagline = input.tagline,
            type = input.type,
            isFavorite = input.isFavorite,
            abilities = input.abilities
        )

    fun heroEntitiesToDomainSingle(input: HeroEntity) : Hero =
        Hero(
            id = input.id,
            name = input.name,
            agiBase = input.agiBase,
            agiGain = input.agiGain,
            armor = input.armor,
            attackType = input.attackType,
            damage = input.damage,
            description = input.description,
            intBase = input.intBase,
            intGain = input.intGain,
            localizedName = input.localizedName,
            movementSpeed = input.movementSpeed,
            strBase = input.strBase,
            strGain = input.strGain,
            tagline = input.tagline,
            type = input.type,
            isFavorite = input.isFavorite,
            abilities = input.abilities
        )

    private fun abilityResponseToEntity(input: List<AbilityResponse>) : List<Ability> =
        input.map {
            Ability(
                id = it.id,
                localizedName = it.localizedName,
                name = it.name,
                description = it.description
            )
        }
}