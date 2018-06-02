package com.trainee.newsapi.screens.base.presenter

import com.trainee.newsapi.screens.base.view.BaseView

interface BasePresenter<V : BaseView> {

    //связать view с presenter
    fun onAttach(view: V)

    //отвязать view от presenter
    fun onDetach()

    //экземпляр текущей view
    fun getView(): V?
}