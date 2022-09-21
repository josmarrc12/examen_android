package com.osmar.examen_android.di

import android.content.Context
import com.osmar.examen_android.data.database.EADatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class EARoomModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(
        @ApplicationContext
        context: Context
    ):EADatabase{
        return EADatabase.newInstance(context)
    }

    @Provides
    fun provideMovieDao(db: EADatabase) = db.movieDao()
}