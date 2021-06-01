package com.example.imagegallery.network

import com.example.imagegallery.model.ImageResponseModel
import javax.inject.Inject

class ApiHelperImpl@Inject constructor(private val apiService: ApiEndPoints) : ApiHelper {

    override suspend fun getImageUrl(page : Int): List<ImageResponseModel> = apiService.getAllImages(page)

}