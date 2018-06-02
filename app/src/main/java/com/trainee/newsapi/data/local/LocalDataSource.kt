package com.trainee.newsapi.data.local

import com.trainee.newsapi.data.local.entity.News
import io.reactivex.Single

interface LocalDataSource {

    //обновление поля isFavourite новости по id, возвращает состояние после обновления
    fun changeFavouriteState(id: String): Boolean

    //загрузка всех новостей
    fun getNews(): Single<List<News>>

    //загрузка деталей новости по id из БД
    fun getNewsById(id: String): Single<News>

    //загрузка всех новостей с таблицы "избранное"
    fun getFavourites(): Single<List<News>>

    //вставка новостей в таблицу "все новости"
    fun insertNews(news: List<News>)

    //удаление всех избранных новостей
    fun deleteAllFavourites()
}