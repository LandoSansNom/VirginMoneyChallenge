package com.example.virginmoneychallenge.data.repository

import com.example.harrypotter.data.remote.ColleaguesCall
import com.example.harrypotter.data.remote.RoomsCall
import com.example.virginmoneychallenge.data.model.Colleague
import com.example.virginmoneychallenge.data.model.Room
import javax.inject.Inject

class RepositoryImplementation @Inject constructor(
    val colleaguesCall: ColleaguesCall,
    val roomsCall: RoomsCall
) : Repository {
    override suspend fun getAllRooms(): List<Room> {
        return roomsCall.getAllRooms()
    }

    override suspend fun getAllColleagues(): List<Colleague> {
        return colleaguesCall.getAllColleagues()

    }


}