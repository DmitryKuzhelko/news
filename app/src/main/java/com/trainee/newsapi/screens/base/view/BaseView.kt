package com.trainee.newsapi.screens.base.view

interface BaseView {

    //показать индикатор загрузки
    fun showLoading()

    //скрыть индикатор загрузки
    fun hideLoading()

    //показать сообщение об ошибке
    fun showError(error : String)
}