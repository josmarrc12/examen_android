package com.osmar.examen_android.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class EAMovie (

val adult: Boolean,
@SerializedName("backdrop_path")
val backdropPath: String,
@PrimaryKey
val id: Long,
@SerializedName("original_language")
val originalLanguage: String,
@SerializedName("original_title")
val originalTitle: String,
val overview: String,
val popularity: Double,
@SerializedName("release_date")
val releaseDate: String,
val title: String,
val video: Boolean,
@SerializedName("vote_average")
val voteAverage: Double,

var type: Int

)