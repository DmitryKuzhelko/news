package com.trainee.newsapi.screens.detail.view

import com.trainee.newsapi.data.local.entity.News
import com.trainee.newsapi.screens.base.view.BaseView

interface DetailView : BaseView {

    fun getDetail(id: String)

    fun changeFavourites(id: String)

    fun showFavouriteState(isFavourite: Boolean)

    fun showDetail(news: News)

    fun showFullNews(url: String)

    fun shareNews()

    fun initToolbar()

    fun initFabFavourites()

    fun initBtnWeb()
}