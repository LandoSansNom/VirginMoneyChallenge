package com.example.harrypotter.data.remote

import com.example.virginmoneychallenge.data.model.Colleague
import com.example.virginmoneychallenge.data.model.Room
import retrofit2.http.GET
import retrofit2.http.Path

interface RoomsCall {
    @GET(ApiDetails.End_Point_Room)
    suspend fun getAllRooms(): List<Room>


}