package com.trainee.newsapi.screens.base.presenter

import com.trainee.newsapi.screens.base.view.BaseView

open class BasePresenterImpl<V : BaseView> : BasePresenter<V> {

    private var view: V? = null

    override fun onAttach(view: V) {
        this.view = view
    }

    override fun onDetach() {
        view = null
    }

    override fun getView(): V? {
        return view
    }
}