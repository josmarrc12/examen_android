package com.osmar.examen_android.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.osmar.examen_android.data.database.dao.EAMovieDao
import com.osmar.examen_android.data.models.EAMovie

@Database(entities = [EAMovie::class], version = 1)
abstract class EADatabase : RoomDatabase() {

    abstract fun movieDao(): EAMovieDao

    companion object{
        @JvmStatic
        fun newInstance(context: Context): EADatabase {
            return Room.databaseBuilder(context,EADatabase::class.java,"ea_database")
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}