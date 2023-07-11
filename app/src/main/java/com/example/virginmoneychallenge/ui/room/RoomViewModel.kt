package com.example.virginmoneychallenge.ui.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.virginmoneychallenge.data.model.Colleague
import com.example.virginmoneychallenge.data.model.Room
import com.example.virginmoneychallenge.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val models: MutableLiveData<List<Room>> by lazy {
        MutableLiveData<List<Room>>()
    }

    var isLoaded = false
    fun getAllRooms(){
        CoroutineScope(Dispatchers.Main).launch {
            var result = repository.getAllRooms()
            models.postValue(result)
            isLoaded = true

        }
    }
}