package com.sap.flickrapp.network

import com.sap.flickrapp.data.PhotoSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("?method=flickr.photos.search&format=json&nojsoncallback=1&api_key=bfe394a49ad97d6a104a41b0ac57ad61")
    suspend fun getData(
        @Query("page") page: Int,
        @Query("text") searchTerm: String
    ): PhotoSearchResponse
}