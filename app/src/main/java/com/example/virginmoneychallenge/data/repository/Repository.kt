package com.example.virginmoneychallenge.data.repository

import com.example.virginmoneychallenge.data.model.Colleague
import com.example.virginmoneychallenge.data.model.Room

interface Repository {
    suspend fun getAllRooms(): List<Room>
    suspend fun getAllColleagues(): List<Colleague>


}