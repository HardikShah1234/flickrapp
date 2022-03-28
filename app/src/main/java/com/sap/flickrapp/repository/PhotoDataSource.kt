package com.sap.flickrapp.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sap.flickrapp.data.PhotoResponse
import com.sap.flickrapp.network.ApiService
import com.sap.flickrapp.utils.Constants.Companion.FIRST_PAGE
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotoDataSource @Inject constructor(
    private val searchTerm: String,
    private val apiService: ApiService,
) : PagingSource<Int, PhotoResponse>() {

    private var page = FIRST_PAGE

    override fun getRefreshKey(state: PagingState<Int, PhotoResponse>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoResponse> {
        val position = params.key ?: page

        return try {

            val response = apiService.getData(params.loadSize, searchTerm)
            val photos = response.photos.photo

            LoadResult.Page(
                data = photos,
                prevKey = if (position == page) {

                    null
                } else position - 1,
                nextKey = if (photos.isEmpty()) {

                    null
                } else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}