package com.trainee.newsapi.data.remote

import com.trainee.newsapi.API_KEY
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url = request.url().newBuilder()
                .addQueryParameter("apiKey", API_KEY)
                .addQueryParameter("sources", "all")
                .build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}