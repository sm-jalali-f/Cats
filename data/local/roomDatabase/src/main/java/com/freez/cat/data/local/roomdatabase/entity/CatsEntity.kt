package com.freez.cat.data.local.roomdatabase.entity

import android.media.Image
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "breeds")
data class CatBreedEntity (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: String,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "origin")
    var origin: String,

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "life_span")
    var life_span: String,

    @ColumnInfo(name = "wikipedia_url")
    var wikipedia_url: String?,

    @ColumnInfo(name = "image")
    var image: Image?,

    @ColumnInfo(name = "temperament")
    var temperament: String

)