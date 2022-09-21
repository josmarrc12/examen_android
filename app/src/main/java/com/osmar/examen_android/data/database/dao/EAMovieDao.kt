package com.osmar.examen_android.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.osmar.examen_android.data.models.EAMovie

@Dao
interface EAMovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: List<EAMovie>)

    @Query("SELECT * FROM EAMovie WHERE type = :type")
    fun getMovies(type:Int):List<EAMovie>?

    @Query("SELECT * FROM EAMovie WHERE id = :id")
    fun getMovieById(id:Long):EAMovie?

}