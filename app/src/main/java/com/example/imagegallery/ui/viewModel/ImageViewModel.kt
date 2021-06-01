package com.example.imagegallery.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.imagegallery.model.ImageResponseModel
import com.example.imagegallery.network.ApiEndPoints
import com.example.imagegallery.network.ApiHelper
import com.example.imagegallery.network.repository.ImagePagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor(private val apiHelper: ApiEndPoints) : ViewModel() {

    val getAllImages: Flow<PagingData<ImageResponseModel>> =
        Pager(config = PagingConfig(10, enablePlaceholders = false)) {
            ImagePagingSource(apiHelper)
        }.flow.cachedIn(viewModelScope)


}