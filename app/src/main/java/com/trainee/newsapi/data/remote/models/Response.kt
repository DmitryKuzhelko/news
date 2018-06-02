package com.trainee.newsapi.data.remote.models

data class Response(
	val totalResults: Int? = null,
	val articles: List<ArticlesItem>? = null,
	val status: String? = null
)