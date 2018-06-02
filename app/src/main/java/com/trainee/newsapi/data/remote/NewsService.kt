package com.trainee.newsapi.data.remote

import com.trainee.newsapi.data.remote.models.Response
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("everything")
    fun requestNewsAll(): Single<Response>

    @GET("everything")
    fun requestNewsByQuery(
            @Query("q") keywords: String,
            @Query("sortBy") sortBy: String,
            @Query("from") from: String,
            @Query("to") to: String
    ): Single<Response>
}