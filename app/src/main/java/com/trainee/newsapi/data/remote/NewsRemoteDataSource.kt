package com.trainee.newsapi.data.remote

import com.trainee.newsapi.SORT_BY_PUBLISHED_AT
import com.trainee.newsapi.convertToNews
import com.trainee.newsapi.data.local.entity.News
import com.trainee.newsapi.data.remote.models.Response
import io.reactivex.Single

class NewsRemoteDataSource(private val apiService: NewsService) : RemoteDataSource {

    override fun getNews(): Single<List<News>> {
        return apiService.requestNewsAll().map { it.articles?.convertToNews() }
    }

    override fun getNewsByQuery(query: String): Single<List<News>> {
        return apiService.requestNewsByQuery(query, SORT_BY_PUBLISHED_AT, "", "").onErrorReturn { Response() }.map { it.articles?.convertToNews() }
    }

    override fun getNewsByCriteria(sortBy: String, from: String, to: String): Single<List<News>> {
        return apiService.requestNewsByQuery(keywords = "", sortBy = sortBy, from = from, to = to).onErrorReturn { Response() }.map { it.articles?.convertToNews() }
    }
}