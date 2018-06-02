package com.trainee.newsapi.screens.favourite.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.trainee.newsapi.FAVOURITE_TAG
import com.trainee.newsapi.R
import com.trainee.newsapi.inTransaction

class HostFavouriteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.host_favourite_activity)

        supportFragmentManager.inTransaction {
            replace(R.id.favourite_fragment_container, FavouriteFragment.newInstance(), FAVOURITE_TAG)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}