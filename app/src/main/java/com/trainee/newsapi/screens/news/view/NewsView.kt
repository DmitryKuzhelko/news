package com.trainee.newsapi.screens.news.view

import com.trainee.newsapi.data.local.entity.News
import com.trainee.newsapi.screens.base.view.BaseView

interface NewsView : BaseView {

    //запрос презентеру на загрузку всех навостей при старте приложения, если не было сохранённого состояния поиска(query : String) при предыдущей работе приложения
    fun loadNews()

    //запрос презентеру на загрузку навостей по ключевому слову
    fun searchNewsByQuery(query: String)

    //запрос презентеру на поиск новостей по выбранным параметрам, вызавается после закрытия FilterActiviry (была нажата кнопка подтверждения на тулбаре)
    fun searchNewsByParam(sortBy: String, from: String, to: String)

    //показать новости загруженные презентером
    fun showNews(news: List<News>)

    //клик по пункту списка(переход к детальному описанию новости)
    fun openDetailScreen(id: String)

    //клик по fab(переход к списку избранных новостей)
    fun openFavouritesScreen()

    //клик по виджету на тулбаре(переход к экрану Filter)
    fun openFilterScreen()

    //отслеживание щелчков
    fun setAdapterClickListener()

    //установка тулбара
    fun initToolbar()

    //подтягивает адаптер и лэяут менеджер
    fun initRecyclerView()

    //вешаем слушатель на кнопку перехода к экрану избранных новостей
    fun initFabFavourites()

    fun setRV(isRV: Boolean)

    //Рома, напиши реализацию этого метода
    fun showSnackbar(isSearch: Boolean)
}