package com.osmar.examen_android.data

import com.osmar.examen_android.data.models.EAListMovies
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface EAApiService {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("language") language: String = EAApiConstants.LANGUAGE,
        @Query("api_key") apiKey: String = EAApiConstants.API_KEY
    ): Response<EAListMovies>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("language") language: String = EAApiConstants.LANGUAGE,
        @Query("api_key") apiKey: String = EAApiConstants.API_KEY
    ): Response<EAListMovies>

    @GET("movie/upcoming")
    suspend fun getUpComingMovies(
        @Query("language") language: String = EAApiConstants.LANGUAGE,
        @Query("api_key") apiKey: String = EAApiConstants.API_KEY
    ): Response<EAListMovies>


}