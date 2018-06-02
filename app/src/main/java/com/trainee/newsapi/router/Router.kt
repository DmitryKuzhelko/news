package com.trainee.newsapi.router

interface Router {

    //передаём параметры для закрузки новостей с сервера(сортировка, источник, страна, категория), параметры могут быть другие,
    fun openNewsScreen(sortBy: String, from: String, to: String)

    //передаём id для загрузки новости из бд
    fun openDetailScreen(id: String)

    //открыть экран избранных
    fun openFavouriteScreen()

    //открыть экран поиска по заданным параметрам
    fun openFilterScreen()
}