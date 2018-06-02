package com.trainee.newsapi.screens.base.view

import android.support.v4.app.Fragment
import android.view.View
import kotlinx.android.synthetic.main.fragment_news_list.*

open class BaseFragment : Fragment(), BaseView {

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    override fun showError(error: String) {
    }
}