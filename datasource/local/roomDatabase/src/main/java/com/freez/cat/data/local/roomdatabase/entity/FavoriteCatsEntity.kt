package com.freez.cat.data.local.roomdatabase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_cats")
data class FavoriteCatsEntity(
    @PrimaryKey(autoGenerate = true) val id: Long?,
    val catId: String,
)
