package com.trainee.newsapi.screens.news.presenter

import com.trainee.newsapi.screens.base.presenter.BasePresenter
import com.trainee.newsapi.screens.news.view.NewsView

interface NewsPresenter<V : NewsView> : BasePresenter<V> {

    //запрос репозиторию на загрузку всех навостей при старте приложения, если не было сохранённого состояния поиска(query : String) при предыдущей работе приложения
    fun loadNews()

    //запрос репозиторию на загрузку при поиске
    fun loadNewsByQuery(query: String)

    //запрос репозиторию на загрузку новостей по заданным параметрам
    fun loadNewsByParam(sortBy: String = "",
                        from: String = "",
                        to: String = "")

    //запросы к роутеру для навигации между экранами
    fun openDetailScreen(id: String)

    fun openFavouritesScreen()

    fun openFilterScreen()
}