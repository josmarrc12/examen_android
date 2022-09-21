package com.osmar.examen_android.data.repositories

import com.osmar.examen_android.common.EAConstants
import com.osmar.examen_android.data.EAApiService
import com.osmar.examen_android.data.EABaseDataSource
import com.osmar.examen_android.data.EANetworkResult
import com.osmar.examen_android.data.database.dao.EAMovieDao
import com.osmar.examen_android.data.models.EAListMovies
import com.osmar.examen_android.data.models.EAMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class EAMoviesRepository @Inject constructor(
    private val apiService: EAApiService,
    private val localDB : EAMovieDao
): EABaseDataSource() {

    suspend fun getPopularMovies() : Flow<EANetworkResult<EAListMovies>> = getResult {
        apiService.getPopularMovies()
    }

    suspend fun getTopRatedMovies() : Flow<EANetworkResult<EAListMovies>> = getResult {
        apiService.getTopRatedMovies()
    }

    suspend fun getUpComingMovies() : Flow<EANetworkResult<EAListMovies>> = getResult {
        apiService.getUpComingMovies()
    }

    fun updateMovies(listMovies: List<EAMovie>?){
        listMovies?.let {
            localDB.insert(listMovies)
        }
    }

    suspend fun getLocalPopularMovies() = flow{
        emit(localDB.getMovies(EAConstants.TYPE_POPULAR))
    }

    suspend fun getLocalTopRatedMovies() = flow{
        emit(localDB.getMovies(EAConstants.TYPE_TOP_RATED))
    }

    suspend fun getLocalUpComingMovies() = flow{
        emit(localDB.getMovies(EAConstants.TYPE_UP_COMING))
    }

    suspend fun getLocalMovieById(id:Long) = flow {
        emit(localDB.getMovieById(id))
    }

}