package com.trainee.newsapi.screens.filter.presenter

import com.trainee.newsapi.screens.base.presenter.BasePresenter
import com.trainee.newsapi.screens.filter.view.FilterView

interface FilterPresenter<V : FilterView> : BasePresenter<V> {

    //Запрос к роутеру закрыть текущий экран и открыть экран со списком новостей, загруженных с сервера по заданным параметрам
    fun openNewsScreen(sortBy: String, from: String, to: String)
}