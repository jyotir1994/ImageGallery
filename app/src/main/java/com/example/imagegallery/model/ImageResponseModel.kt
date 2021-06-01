package com.example.imagegallery.model

import com.squareup.moshi.Json
import java.io.Serializable

data class ImageResponseModel(

    @Json(name = "download_url")
    var download_url : String,
) : Serializable
