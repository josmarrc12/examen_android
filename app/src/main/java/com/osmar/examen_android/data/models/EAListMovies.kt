package com.osmar.examen_android.data.models


import com.google.gson.annotations.SerializedName


data class EAListMovies (
    val page: Long,
    @SerializedName("results")
    val movies: List<EAMovie>,
    @SerializedName("total_pages")
    val totalPages: Long,
    @SerializedName("total_results")
    val totalResults: Long
)