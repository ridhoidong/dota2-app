package com.idong.core.data.source.local.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.idong.core.data.source.local.entity.Ability

/**
 * Created by ridhopratama on 29,August,2021
 */
class RoomConverter {

    @TypeConverter
    fun fromListAbility(value : List<Ability>) : String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toListAbility(value : String) : List<Ability> {
        val listType = object : TypeToken<List<Ability>>() {}.type
        return Gson().fromJson(value, listType)
    }

}