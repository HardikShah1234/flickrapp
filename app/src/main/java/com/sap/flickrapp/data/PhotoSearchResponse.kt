package com.sap.flickrapp.data

data class PhotoSearchResponse(
    val photos: PhotosMetaData
)

data class PhotosMetaData(
    val page: Int,
    val pages: Int,
    val photo: List<PhotoResponse>
)

data class PhotoResponse(
    val farm: Int,
    val id: String,
    val isfamily: Int,
    val isfriend: Int,
    val ispublic: Int,
    val owner: String,
    val secret: String,
    val server: String,
    val title: String
)
