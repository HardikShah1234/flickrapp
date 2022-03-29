package com.sap.flickrapp.models

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.paging.PagingData
import com.sap.flickrapp.data.PhotoResponse
import com.sap.flickrapp.repository.PagedListRepository
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MainActivityViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var observer: Observer<PagingData<PhotoResponse>>

    @MockK
    lateinit var repository: PagedListRepository
    private lateinit var mainActivityViewModel: MainActivityViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        mainActivityViewModel = MainActivityViewModel(repository)
        mainActivityViewModel.photos.observeForever(observer)
    }

    @Test
    fun `Perform Search`() {
        mainActivityViewModel.performSearch("Car")
        assertNotNull(mainActivityViewModel.searchQuery.value)
        assertEquals("Car", mainActivityViewModel.searchQuery.value)
    }
}