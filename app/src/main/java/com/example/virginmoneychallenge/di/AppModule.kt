package com.example.virginmoneychallenge.di

import com.example.harrypotter.data.remote.ApiDetails
import com.example.harrypotter.data.remote.ColleaguesCall
import com.example.harrypotter.data.remote.RoomsCall
import com.example.virginmoneychallenge.data.repository.Repository
import com.example.virginmoneychallenge.data.repository.RepositoryImplementation
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
    fun provideAPIColleague(
        retrofit: Retrofit
    ): ColleaguesCall {
        return retrofit.create(ColleaguesCall::class.java)
    }

    @Provides
    fun provideAPIRoom(
        retrofit: Retrofit
    ): RoomsCall {
        return retrofit.create(RoomsCall::class.java)
    }

    @Provides
    fun provideRepository(
        colleaguesCall: ColleaguesCall,
        roomsCall: RoomsCall
    ): Repository {
        return RepositoryImplementation(colleaguesCall, roomsCall)
    }

}