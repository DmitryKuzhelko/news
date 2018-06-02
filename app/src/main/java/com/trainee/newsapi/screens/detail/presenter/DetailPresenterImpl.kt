package com.trainee.newsapi.screens.detail.presenter

import android.util.Log
import com.trainee.newsapi.data.NewsRepository
import com.trainee.newsapi.data.local.entity.News
import com.trainee.newsapi.router.Router
import com.trainee.newsapi.screens.base.presenter.BasePresenterImpl
import com.trainee.newsapi.screens.detail.view.DetailView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DetailPresenterImpl<V : DetailView>
@Inject constructor(private val repository: NewsRepository, private val router: Router) : BasePresenterImpl<V>(), DetailPresenter<V> {
    lateinit var news: News
    override fun loadNewsById(id: String) {
        repository.getNewsById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ news ->
                    getView()?.showDetail(news)
                    this.news = news
                }, {
                    Log.i("DetailPresenterImpl", "error in loadNewsById(id: Long) method: ${it.message}")
                })
    }

    override fun showFullNews(id: String){
        repository.getNewsById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ news ->
                    getView()?.showFullNews(news.url)
                }, {
                    Log.i("DetailPresenterImpl", "error in showFullNews(id: Long) method: ${it.message}")
                })
    }

    override fun changeFavourites(id: String) {
        showChangesFavourites(repository.changeFavouriteState(id))
    }

    override fun showChangesFavourites(state: Boolean) {
        //getView()?.showFavouriteState(state)
    }
}