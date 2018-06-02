package com.trainee.newsapi.screens.favourite.presenter

import com.trainee.newsapi.data.NewsRepository
import com.trainee.newsapi.data.local.entity.News
import com.trainee.newsapi.router.Router
import com.trainee.newsapi.screens.base.presenter.BasePresenterImpl
import com.trainee.newsapi.screens.favourite.view.FavouriteView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FavouritePresenterImpl<V : FavouriteView>
@Inject constructor(private val repository: NewsRepository, private val router: Router) : BasePresenterImpl<V>(), FavouritePresenter<V> {

    override fun deleteAllFavourites() {
        repository.deleteAllFavourites()
        getView()?.showNews(ArrayList<News>())
    }

    override fun openDetailScreen(id: String) {
        router.openDetailScreen(id)
    }

    override fun loadFavourites() {
        getView()?.showLoading()
        repository.getFavourites()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { news ->
                    getView()?.showNews(news)
                    getView()?.hideLoading()
                }
    }
}