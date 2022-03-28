package com.sap.flickrapp.models

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.sap.flickrapp.repository.NetworkState
import com.sap.flickrapp.repository.PagedListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val repository: PagedListRepository
) : ViewModel() {

    val searchQuery = MutableLiveData<String>()

    val photos =
        searchQuery.switchMap {
            repository.fetchPhotoPagedList(it).cachedIn(viewModelScope)
        }

    fun performSearch(searchTerm: String) = searchQuery.postValue(searchTerm)
}