package com.trainee.newsapi.data.remote.models

data class ArticlesItem(
        val publishedAt: String?,
        val author: String?,
        val urlToImage: String?,
        val description: String?,
        val source: Source?,
        val title: String?,
        val url: String?
)