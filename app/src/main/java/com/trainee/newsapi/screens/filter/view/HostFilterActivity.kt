package com.trainee.newsapi.screens.filter.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.trainee.newsapi.FILTER_TAG
import com.trainee.newsapi.R
import com.trainee.newsapi.inTransaction

class HostFilterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.host_filter_activity)

        supportFragmentManager.inTransaction {
            replace(R.id.filter_fragment_container, FilterFragment.newInstance(), FILTER_TAG)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}