package com.trainee.newsapi.data

import com.trainee.newsapi.data.local.entity.News
import io.reactivex.Single

interface NewsDataSource {

    //изменить состояние isFavourite на противоположное (нажатие на кнопку добавления в избранное)
    fun changeFavouriteState(id: String): Boolean

    //загрузка всех новостей c БД (оффлайн работа)
    fun getNewsFromBD(): Single<List<News>>

    //загрузка всех новостей с сервера при старте приложения, если ранее не был сохранён запрос по ключевому слову
    fun getNews(): Single<List<News>>

    //сохранение новостей в БД после загрузки из сети (для оффлайн работы)
    fun insertNews(news: List<News>)

    //загрузка деталей выбранной новости из БД
    fun getNewsById(id: String): Single<News>

    //загрузка с сервера новостей по ключевому слову(поиск на стртовом экране)
    fun getNewsByQuery(query: String): Single<List<News>>

    //загрузка с сервера новостей по выбранным параметрам
    fun getNewsByCriteria(sortBy: String,
                          from: String,
                          to: String): Single<List<News>>

    //загрузка избранных новостей из БД
    fun getFavourites(): Single<List<News>>

    //установить поле isFavourite = false для всех избранных новостей
    fun deleteAllFavourites()
}