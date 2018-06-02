package com.trainee.newsapi.screens.favourite.view

import com.trainee.newsapi.data.local.entity.News
import com.trainee.newsapi.screens.base.view.BaseView

interface FavouriteView : BaseView {

    fun loadFavouritesNews()

    fun showNews(news: List<News>)

    fun deleteAllFavourites()

    fun initToolbar()

    fun initRecyclerView()

    fun setAdapterClickListener()

    fun openDetailScreen(id: String)
}