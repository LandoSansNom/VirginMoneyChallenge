package com.example.harrypotter.data.remote

import com.example.virginmoneychallenge.data.model.Colleague
import retrofit2.http.GET
import retrofit2.http.Path

interface ColleaguesCall {
    @GET(ApiDetails.End_Point_Colleagues)
    suspend fun getAllColleagues(): List<Colleague>


}