package com.example.virginmoneychallenge.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.harrypotter.data.remote.ColleaguesCall
import com.example.harrypotter.data.remote.RoomsCall
import com.example.virginmoneychallenge.data.model.Colleague
import com.example.virginmoneychallenge.data.model.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

class RepositoryImplementationTest{

    // allow me to use the threads
    private val testDispatcher = StandardTestDispatcher()

    // allow me to run tasks on threads with priority
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var repository: Repository

    @Mock
    lateinit var colleaguesCall: ColleaguesCall

    @Mock
    lateinit var roomsCall: RoomsCall


    @Before
    fun startup() {
        MockitoAnnotations.openMocks(this) // initialize all the mocks that we have in THIS class
        Dispatchers.setMain(testDispatcher) // assume this as the main thread for testing

        repository = RepositoryImplementation(colleaguesCall, roomsCall)
    }


    @Test
    fun getAllRooms_Success() = runTest {
        val mockResponse = mutableListOf<Room>()
        mockResponse.add(0, Room("2022-01-24T20:52:50.765Z","1",false,2350))
        mockResponse.add(1, Room("2022-01-24T20:52:50.765Z","2",true,2350))

        // defining the API response in MOCK
        Mockito.`when`(roomsCall.getAllRooms()).thenReturn(mockResponse)

        val result = repository.getAllRooms()

        assertEquals(result.get(0).id, mockResponse.get(0).id)
    }

    @Test
    fun getAllColleagues_Success() = runTest {
        val mockResponse = mutableListOf<Colleague>()
        mockResponse.add(0, Colleague(firstName = "Cherlan-Miche"))
        mockResponse.add(1, Colleague(firstName = "Guirand"))

        // defining the API response in MOCK
        Mockito.`when`(colleaguesCall.getAllColleagues()).thenReturn(mockResponse)

        val result = repository.getAllColleagues()

        assertEquals(result.get(0).firstName, mockResponse.get(0).firstName)
    }
}