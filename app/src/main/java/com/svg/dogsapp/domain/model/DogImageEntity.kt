package com.svg.dogsapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "dogs")
data class DogImageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val url: String,
    val bitmapBytes: ByteArray,
    val timestamp: Long
)