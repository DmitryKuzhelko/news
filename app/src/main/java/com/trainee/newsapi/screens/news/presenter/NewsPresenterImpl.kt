package com.trainee.newsapi.screens.news.presenter

import android.util.Log
import com.trainee.newsapi.data.NewsRepository
import com.trainee.newsapi.router.Router
import com.trainee.newsapi.screens.base.presenter.BasePresenterImpl
import com.trainee.newsapi.screens.news.view.NewsView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NewsPresenterImpl<V : NewsView>
@Inject constructor(private val repository: NewsRepository, private val router: Router) : BasePresenterImpl<V>(), NewsPresenter<V> {

    override fun loadNews() {
        getView()?.showLoading()
        repository.getNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(({ t ->
                    getView()?.apply {
                        showNews(t)
                        hideLoading()
                    }
                }), ({
                    getView()?.apply {
                        showSnackbar(false)
                        hideLoading()
                    }
                }))
    }

    override fun loadNewsByQuery(query: String) {
        getView()?.showLoading()
        repository.getNewsByQuery(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(({ t ->
                    getView()?.apply {
                        showNews(t)
                        repository.insertNews(t)
                        hideLoading()
                    }
                }), ({
                    getView()?.apply {
                        showSnackbar(true)
                        hideLoading()
                    }
                }))
    }

    override fun loadNewsByParam(sortBy: String, from: String, to: String) {
        repository.getNewsFromBD()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(({ t ->
                    getView()?.apply {
                        showNews(t)
                        setRV(t.isNotEmpty())
                    }
                }))
        getView()?.showLoading()
        repository.getNewsByCriteria(sortBy, from, to)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(({ t ->
                    getView()?.apply {
                        showNews(t)
                        repository.insertNews(t)
                        hideLoading()
                        setRV(t.isNotEmpty())
                    }
                }), ({
                    Log.d("TAG",it.localizedMessage.toString())
                    getView()?.apply {
                        showSnackbar(false)
                        hideLoading()
                    }
                }))
    }

    override fun openDetailScreen(id: String) {
        router.openDetailScreen(id)
    }

    override fun openFavouritesScreen() {
        router.openFavouriteScreen()
    }

    override fun openFilterScreen() {
        router.openFilterScreen()
    }

}