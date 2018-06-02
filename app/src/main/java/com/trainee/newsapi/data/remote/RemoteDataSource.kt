package com.trainee.newsapi.data.remote

import com.trainee.newsapi.data.local.entity.News
import io.reactivex.Single

interface RemoteDataSource {
    fun getNews(): Single<List<News>>

    //загрузка с сервера новостей по ключевому слову(поиск на стртовом экране)
    fun getNewsByQuery(query: String): Single<List<News>>

    //загрузка с сервера новостей по выбранным параметрам
    fun getNewsByCriteria(sortBy: String,
                          from: String,
                          to: String): Single<List<News>>
}