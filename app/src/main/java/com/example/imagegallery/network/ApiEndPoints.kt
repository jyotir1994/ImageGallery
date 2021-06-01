package com.example.imagegallery.network

import com.example.imagegallery.model.ImageResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiEndPoints {

    @GET(Url.IMAGE)
    suspend fun getAllImages(
        @Query("page") page: Int
    ): List<ImageResponseModel>
}