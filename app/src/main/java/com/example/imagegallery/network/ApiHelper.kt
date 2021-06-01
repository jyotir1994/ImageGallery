package com.example.imagegallery.network

import com.example.imagegallery.model.ImageResponseModel
import retrofit2.Response

interface ApiHelper {
    suspend fun getImageUrl(page : Int): List<ImageResponseModel>
}