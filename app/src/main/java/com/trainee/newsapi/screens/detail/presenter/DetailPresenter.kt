package com.trainee.newsapi.screens.detail.presenter

import com.trainee.newsapi.screens.base.presenter.BasePresenter
import com.trainee.newsapi.screens.detail.view.DetailView

interface DetailPresenter<V : DetailView> : BasePresenter<V> {

    fun loadNewsById(id: String)

    fun showFullNews(id: String)

    fun changeFavourites(id: String)

    fun showChangesFavourites(state: Boolean)
}