package com.vaibhav.robin.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.vaibhav.robin.data.repository.Repository
import com.vaibhav.robin.data.unsplash.model.Results
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery

    private val _searchedImages = MutableStateFlow<PagingData<Results>>(PagingData.empty())
    val searchedImages = _searchedImages

    var job:Job=Job()
    fun search() {
        job.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            _searchQuery.collect { query ->
                if (!query.isNullOrBlank()) {
                    Repository().searchImages(query = _searchQuery.value).cachedIn(viewModelScope)
                        .collect {
                            _searchedImages.value = it
                        }
                }
            }
        }
    }
    fun updateQuery(string: String)= run { _searchQuery.value=string }
}
