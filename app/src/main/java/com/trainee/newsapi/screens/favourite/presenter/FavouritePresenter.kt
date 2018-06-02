package com.trainee.newsapi.screens.favourite.presenter

import com.trainee.newsapi.screens.base.presenter.BasePresenter
import com.trainee.newsapi.screens.favourite.view.FavouriteView

interface FavouritePresenter<V : FavouriteView> : BasePresenter<V> {

    fun loadFavourites()

    fun deleteAllFavourites()

    fun openDetailScreen(id: String)
}