package com.example.virginmoneychallenge.data.di

import com.example.harrypotter.data.remote.harryPotterCall
import com.example.harrypotter.data.remote.ApiDetails
import com.example.harrypotter.data.repository.Repository
import com.example.harrypotter.data.repository.RepositoryImplementation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun provideOkHttpInstance(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Provides
    fun provideRetrofitInstance(
        client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiDetails.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    fun provideAPI(
        retrofit: Retrofit
    ): harryPotterCall {
        return retrofit.create(harryPotterCall::class.java)
    }

    @Provides
    fun provideRepository(
        harryPotterCall: harryPotterCall
    ): Repository{
        return RepositoryImplementation(harryPotterCall)
    }

}