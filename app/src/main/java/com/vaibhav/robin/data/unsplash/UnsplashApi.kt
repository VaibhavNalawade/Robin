package com.vaibhav.robin.data.unsplash

import android.util.Log
import com.vaibhav.robin.BuildConfig
import com.vaibhav.robin.data.repository.RobinDataSource
import com.vaibhav.robin.data.unsplash.model.Results
import com.vaibhav.robin.data.unsplash.model.UnsplashGet
import com.vaibhav.robin.network.RobinHttpClient
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*


class UnsplashApi:RobinDataSource {
    companion object {
        private const val apiAddress = "https://api.unsplash.com"
        private const val searchEndpoint = "$apiAddress/search/photos"
        fun getCollectionEndpoint(id: String) = "$apiAddress/collections/$id/photos"
        private const val TAG = "UnsplashApi"
    }

    suspend fun searchImages(query: String, perPage: Int, pageNumber: Int): UnsplashGet =
        RobinHttpClient.client.request(searchEndpoint) {
            Log.d(TAG, "PerPage-$perPage pageNumber-$pageNumber query=$query")
            url {
                protocol = URLProtocol.HTTPS
                host = "api.unsplash.com"
            }
            headers {
                append(HttpHeaders.Accept, "text/html")
                append(HttpHeaders.Authorization, "token")
                append(HttpHeaders.UserAgent, "Android ktor client")
            }
            parameter("client_id", BuildConfig.API_KEY)
            parameter("query", query)
            parameter("per_page", perPage)
            parameter("page", pageNumber)
            parameter("orientation", "landscape")

            method = HttpMethod.Get
        }.body()

    suspend fun getCollection(collectionId: String, perPage: Int, pageNumber: Int): List<Results> =
        RobinHttpClient.client.request(getCollectionEndpoint(collectionId)) {
            Log.d(TAG, "PerPage-$perPage pageNumber-$pageNumber CollectionID=$collectionId")
            url {
                protocol = URLProtocol.HTTPS
                host = "api.unsplash.com"
            }
            headers {
                append(HttpHeaders.Accept, "text/html")
                append(HttpHeaders.Authorization, "token")
                append(HttpHeaders.UserAgent, "Android ktor client")
            }
            parameter("client_id", BuildConfig.API_KEY)
            parameter("per_page", perPage)
            parameter("page", pageNumber)

            method = HttpMethod.Get
        }.body()
}
