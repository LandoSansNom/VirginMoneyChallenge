package com.example.virginmoneychallenge.ui.colleagues

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.virginmoneychallenge.data.model.Colleague
import com.example.virginmoneychallenge.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class ColleagueViewModelTest {

    // allow me to use the threads
    private val testDispatcher = StandardTestDispatcher()

    // allow me to run tasks on threads with priority
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: ColleagueViewModel

    @Mock
    private lateinit var repository: Repository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this) // initialize all the mocks that we have in THIS class
        Dispatchers.setMain(testDispatcher) // assume this as the main thread for testing
        viewModel = ColleagueViewModel(repository)
    }

//    @After
//    fun tearDown() {
//        instantTaskExecutorRule.cleanupTestCoroutines()
//    }

    @Test
    fun `getAllColleaguesSuccess`() = runTest {
        // Mock the response from the repository
        val expectedResult = listOf(Colleague())
        Mockito.`when`(repository.getAllColleagues()).thenReturn(expectedResult)


        // Call the method to be tested
        viewModel.getAllColleagues()

        // Verify the expected behavior
        assert(viewModel.isLoading.value == true)

        assert(viewModel.models.value == expectedResult)
        assert(viewModel.isLoaded)
        assert(viewModel.isLoading.value == false)
    }

}