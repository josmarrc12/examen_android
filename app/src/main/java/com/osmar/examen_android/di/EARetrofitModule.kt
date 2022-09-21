package com.osmar.examen_android.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.osmar.examen_android.data.EAApiConstants
import com.osmar.examen_android.data.EAApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
class EARetrofitModule {

    @Provides
    fun provideRetrofit(@ApplicationContext appContext: Context, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(EAApiConstants.SERVER_PATH)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    fun provideApiService(retrofit: Retrofit): EAApiService = retrofit.create(
        EAApiService::class.java)

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()





}