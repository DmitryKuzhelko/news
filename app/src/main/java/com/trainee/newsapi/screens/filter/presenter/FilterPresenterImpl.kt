package com.trainee.newsapi.screens.filter.presenter

import com.trainee.newsapi.data.NewsRepository
import com.trainee.newsapi.router.Router
import com.trainee.newsapi.screens.base.presenter.BasePresenterImpl
import com.trainee.newsapi.screens.filter.view.FilterView
import javax.inject.Inject

class FilterPresenterImpl<V : FilterView>
@Inject constructor(private val repository: NewsRepository, private val router: Router) : BasePresenterImpl<V>(), FilterPresenter<V> {

    override fun openNewsScreen(sortBy: String, from: String, to: String) {
        router.openNewsScreen(sortBy, from, to)
    }
}