package com.trainee.newsapi.data

import com.trainee.newsapi.data.local.LocalDataSource
import com.trainee.newsapi.data.local.entity.News
import com.trainee.newsapi.data.remote.RemoteDataSource
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class NewsRepository(private val local: LocalDataSource, private val remote: RemoteDataSource) : NewsDataSource {

    //изменить состояние isFavourite на противоположное (нажатие на кнопку добавления в избранное)
    override fun changeFavouriteState(id: String): Boolean {
        return local.changeFavouriteState(id)
    }

    //загрузка всех новостей из БД (оффлайн работа)
    override fun getNewsFromBD(): Single<List<News>> {
        return local.getNews()
    }

    //загружаем из сети новости и сохраняем в базу
    override fun getNews(): Single<List<News>> {
        val loadedListNews: Single<List<News>> = remote.getNews()
        loadedListNews
                .subscribeOn(Schedulers.io())
                .subscribe({ news -> insertNews(news) })
        return loadedListNews
    }

    //сохранение новостей в БД после загрузки из сети (для оффлайн работы)
    override fun insertNews(news: List<News>) {
        local.insertNews(news)
    }

    //загрузка деталей новости по id из БД
    override fun getNewsById(id: String): Single<News> {
        return local.getNewsById(id)
    }

    override fun getNewsByQuery(query: String): Single<List<News>> {
        return remote.getNewsByQuery(query)
    }


    override fun getNewsByCriteria(sortBy: String, from: String, to: String): Single<List<News>> {
        return remote.getNewsByCriteria(sortBy, from, to)
    }

    //загрузка всех избранных(isFavourite = true) новостей из БД
    override fun getFavourites(): Single<List<News>> {
        return local.getFavourites()
    }

    //установить поле isFavourite = false для всех избранных новостей
    override fun deleteAllFavourites() {
        local.deleteAllFavourites()
    }
}