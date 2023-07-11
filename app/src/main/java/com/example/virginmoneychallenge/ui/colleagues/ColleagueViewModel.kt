package com.example.virginmoneychallenge.ui.colleagues

import androidx.lifecycle.LiveData
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
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    val models: MutableLiveData<List<Colleague>> by lazy {
        MutableLiveData<List<Colleague>>()
    }

    var isLoaded = false
    fun getAllColleagues(){
        CoroutineScope(Dispatchers.Main).launch {
            _isLoading.value = true
            var result = repository.getAllColleagues()
            models.postValue(result)
            isLoaded = true
            _isLoading.value = false

        }
    }
}