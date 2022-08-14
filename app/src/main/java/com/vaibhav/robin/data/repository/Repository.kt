package com.vaibhav.robin.data.repository

import androidx.compose.runtime.MutableState
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.vaibhav.robin.data.paging.SearchPagingSource
import com.vaibhav.robin.entities.remote.BannerImage
import com.vaibhav.robin.data.unsplash.UnsplashApi
import com.vaibhav.robin.data.unsplash.model.Results
import kotlinx.coroutines.flow.Flow

class Repository {

    fun searchImages(query: String): Flow<PagingData<Results>> {
        return Pager(
            config = PagingConfig(pageSize = 30),
            pagingSourceFactory = {
                SearchPagingSource(UnsplashApi(), query = query)
            }
        ).flow
    }
}
