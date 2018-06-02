package com.trainee.newsapi.router

import android.content.Context
import android.content.Intent
import com.trainee.newsapi.*
import com.trainee.newsapi.screens.detail.view.HostDetailActivity
import com.trainee.newsapi.screens.favourite.view.HostFavouriteActivity
import com.trainee.newsapi.screens.filter.view.HostFilterActivity
import com.trainee.newsapi.screens.news.view.HostNewsActivity

class RouterImpl(private val context: Context) : Router {

    override fun openNewsScreen(sortBy: String, from: String, to: String) {
        context.startActivity(Intent(context, HostNewsActivity::class.java).apply {
            putExtra(SORT_BY, sortBy)
            putExtra(FROM_DATE, from)
            putExtra(TO_DATE, to)
        })
    }

    override fun openDetailScreen(id: String) {
        context.startActivity(Intent(context, HostDetailActivity::class.java)
                .putExtra(NEWS_ID, id))
    }

    override fun openFavouriteScreen() {
        context.startActivity(Intent(context, HostFavouriteActivity::class.java))
    }

    override fun openFilterScreen() {
        context.startActivity(Intent(context, HostFilterActivity::class.java))
    }
}