package com.sap.flickrapp.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.sap.flickrapp.network.ApiService
import com.sap.flickrapp.utils.Constants.Companion.POST_PER_PAGE
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PagedListRepository @Inject constructor(
    private val apiService: ApiService,
) {

    fun fetchPhotoPagedList(searchTerm: String) =
        Pager(
            config = PagingConfig(
                pageSize = POST_PER_PAGE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PhotoDataSource(searchTerm, apiService) }
        ).liveData

}