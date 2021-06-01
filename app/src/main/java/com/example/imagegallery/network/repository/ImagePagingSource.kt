package com.example.imagegallery.network.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.imagegallery.constants.AppConstants
import com.example.imagegallery.constants.AppConstants.IMAGE_STARTING_PAGE_INDEX
import com.example.imagegallery.model.ImageResponseModel
import com.example.imagegallery.network.ApiEndPoints
import retrofit2.HttpException
import java.io.IOException

class ImagePagingSource constructor(private val apiHelper: ApiEndPoints) : PagingSource<Int, ImageResponseModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImageResponseModel> {
        val pageIndex = params.key ?: AppConstants.IMAGE_STARTING_PAGE_INDEX
        return try {
            val response = apiHelper.getAllImages(pageIndex)
            LoadResult.Page(
                response,
                prevKey = if(pageIndex == IMAGE_STARTING_PAGE_INDEX) null else pageIndex-1,
                nextKey = if(response.isEmpty()) null else pageIndex+1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    /**
     * The refresh key is used for subsequent calls to PagingSource.Load after the initial load.
     */
    override fun getRefreshKey(state: PagingState<Int, ImageResponseModel>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index.
        return  null /*state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }*/
    }
}