package com.vaibhav.robin.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vaibhav.robin.data.unsplash.model.Results
import com.vaibhav.robin.data.unsplash.UnsplashApi

class SearchPagingSource(
    private val unsplashApi: UnsplashApi,
    private val query: String
) : PagingSource<Int, Results>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Results> {
        val currentPage = params.key ?: 1
        return try {
            Log.e("TAN", "Man")
            val response = unsplashApi.searchImages(query = query, perPage = 30, currentPage)
            val endOfPaginationReached = response.results.isEmpty()
            if (response.results.isNotEmpty()) {
                LoadResult.Page(
                    data = response.results,
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = if (endOfPaginationReached) null else currentPage + 1
                )
            } else {
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Results>): Int? {
        return state.anchorPosition
    }

}