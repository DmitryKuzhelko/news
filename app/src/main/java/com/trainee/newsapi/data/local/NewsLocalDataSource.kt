package com.trainee.newsapi.data.local

import com.trainee.newsapi.compareById
import com.trainee.newsapi.data.local.entity.News
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.experimental.launch

class NewsLocalDataSource(private val database: NewsDatabase) : LocalDataSource {

    override fun changeFavouriteState(id: String): Boolean {
        var state = false
        database.newsDao().getNewsById(id)
                .subscribeOn(Schedulers.io())
                .subscribe({ news ->
                    news.isFavourite = !news.isFavourite
                    state = news.isFavourite
                    database.newsDao().updateFavouriteState(news)
                })
        return state
    }

    //загрузка всех новостей
    override fun getNews(): Single<List<News>> {
        return database.newsDao().getNews()
    }

    //загрузка деталей новости по id из БД
    override fun getNewsById(id: String): Single<News> {
        return database.newsDao().getNewsById(id)
    }

    //загрузка всех избранных(isFavourite = true) новостей из БД
    override fun getFavourites(): Single<List<News>> {
        return database.newsDao().getFavorites(true)
    }

    //вставка всех новостей в базу после запуска приложения/запроса по ключ.слову/запроса по параметрам.
    // При вставке у всех загружаемых новостей сравнивается поле isFavourite с аналогичным полей новостей,
    // хранимых в базе(подгружаются список Id новостей с полем isFavourite).
    override fun insertNews(news: List<News>) {
        launch {
            database.newsDao().deleteAll(false)
            database.newsDao().insertAll(news.compareById(database.newsDao().getFavoritesNews(true)))
        }
    }

    override fun deleteAllFavourites() {
        launch {
            database.newsDao().deleteAllFavorites()
        }
    }
}