package com.trainee.newsapi.data.local

import android.arch.persistence.room.*
import com.trainee.newsapi.data.local.entity.News
import io.reactivex.Single

@Dao
interface NewsDao {

    //обновление состоянием поля isFavourite переданной новости
    @Update
    fun updateFavouriteState(news: News)

    //возврат всех новостей (оффлайн работа)
    @Query("SELECT * FROM news")
    fun getNews(): Single<List<News>>

    //возврат новости по id (и при онлайн и оффлайн работе берёт данные из БД)
    @Query("SELECT * FROM news WHERE id = :id")
    fun getNewsById(id: String): Single<News>

    //вставка всех записей загруженных из сети при запуске приложения/после запроса по ключ.слову/запроса
    // по параметрам (если среди загружаемых есть избранные новости, то их не грузим, т.к. онии уже есть в базе)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(news: List<News>)

    @Query("SELECT * FROM news WHERE isFavourite = :isFavourite")
    fun getFavorites(isFavourite: Boolean): Single<List<News>>

    // удаляем все новостей, кроме избранных (isFavourite = false), вызывается при подгрузки новых данных из сети
    @Query("DELETE FROM news WHERE isFavourite = :isFavourite")
    fun deleteAll(isFavourite: Boolean)

    //
    @Query("DELETE FROM news")
    fun deleteAllFavorites()

    //возвращает список избанных новостей для сравнения с их id с id новостей загруженных из сети
    @Query("SELECT * FROM news WHERE isFavourite = :isFavourite")
    fun getFavoritesNews(isFavourite: Boolean): List<News>
}