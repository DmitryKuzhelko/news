package com.trainee.newsapi.di

import android.content.Context
import com.trainee.newsapi.screens.detail.view.DetailFragment
import com.trainee.newsapi.screens.favourite.view.FavouriteFragment
import com.trainee.newsapi.screens.filter.view.FilterFragment
import com.trainee.newsapi.screens.news.view.NewsFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(newsFragment: NewsFragment)

    fun inject(detailFragment: DetailFragment)

    fun inject(filterFragment: FilterFragment)

    fun inject(favouriteFragment: FavouriteFragment)

    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        @BindsInstance
        fun application(context: Context): Builder
    }
}