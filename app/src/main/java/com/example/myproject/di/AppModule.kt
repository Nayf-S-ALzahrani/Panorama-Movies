package com.example.myproject.di

import android.app.Application
import android.content.Context
import com.example.myproject.common.Constants
import com.example.myproject.data.remote.ImdbApi
import com.example.myproject.data.repository.MovieAndSeriesRepositoryImpl
import com.example.myproject.domain.repository.MovieAndSeriesRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideImdpApi(): ImdbApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ImdbApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(api: ImdbApi): MovieAndSeriesRepository {
        return MovieAndSeriesRepositoryImpl(api)
    }


    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }



}