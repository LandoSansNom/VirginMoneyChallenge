package com.example.virginmoneychallenge.ui.colleagues

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.virginmoneychallenge.data.model.Colleague
import com.example.virginmoneychallenge.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ColleagueViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val models: MutableLiveData<List<Colleague>> by lazy {
        MutableLiveData<List<Colleague>>()
    }

    var isLoaded = false
    fun getAllColleagues(){
        CoroutineScope(Dispatchers.Main).launch {
            var result = repository.getAllColleagues()
            models.postValue(result)
            isLoaded = true
        }
    }
}