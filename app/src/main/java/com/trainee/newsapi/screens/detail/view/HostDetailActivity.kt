package com.trainee.newsapi.screens.detail.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.trainee.newsapi.DETAIL_TAG
import com.trainee.newsapi.NEWS_ID
import com.trainee.newsapi.R
import com.trainee.newsapi.inTransaction

class HostDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.host_detail_activity)

        //id новости, пришедшей с главного экрана или с экрана избранных новостей, если ничего не пришло, то по дефолту вернётся -1L
        val newsId = intent.getStringExtra(NEWS_ID)

        supportFragmentManager.inTransaction {
            replace(R.id.detail_fragment_container, DetailFragment.newInstance(newsId), DETAIL_TAG)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}