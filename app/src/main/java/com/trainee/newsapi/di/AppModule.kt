package com.trainee.newsapi.di

import android.arch.persistence.room.Room
import android.content.Context
import com.google.gson.GsonBuilder
import com.trainee.newsapi.DATABASE_NAME
import com.trainee.newsapi.URL_API
import com.trainee.newsapi.data.NewsRepository
import com.trainee.newsapi.data.local.NewsDatabase
import com.trainee.newsapi.data.local.NewsLocalDataSource
import com.trainee.newsapi.data.remote.ApiKeyInterceptor
import com.trainee.newsapi.data.remote.NewsRemoteDataSource
import com.trainee.newsapi.data.remote.NewsService
import com.trainee.newsapi.router.Router
import com.trainee.newsapi.router.RouterImpl
import com.trainee.newsapi.screens.NewsAdapter
import com.trainee.newsapi.screens.detail.presenter.DetailPresenter
import com.trainee.newsapi.screens.detail.presenter.DetailPresenterImpl
import com.trainee.newsapi.screens.detail.view.DetailView
import com.trainee.newsapi.screens.favourite.presenter.FavouritePresenter
import com.trainee.newsapi.screens.favourite.presenter.FavouritePresenterImpl
import com.trainee.newsapi.screens.favourite.view.FavouriteView
import com.trainee.newsapi.screens.filter.presenter.FilterPresenter
import com.trainee.newsapi.screens.filter.presenter.FilterPresenterImpl
import com.trainee.newsapi.screens.filter.view.FilterView
import com.trainee.newsapi.screens.news.presenter.NewsPresenter
import com.trainee.newsapi.screens.news.presenter.NewsPresenterImpl
import com.trainee.newsapi.screens.news.view.NewsView
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {

    //provides presenters
    @Singleton
    @Provides
    fun provideNewsPresenter(repository: NewsRepository, router: Router): NewsPresenter<*> = NewsPresenterImpl<NewsView>(repository, router)

    @Singleton
    @Provides
    fun provideDetailPresenter(repository: NewsRepository, router: Router): DetailPresenter<*> = DetailPresenterImpl<DetailView>(repository, router)

    @Singleton
    @Provides
    fun provideFavouritePresenter(repository: NewsRepository, router: Router): FavouritePresenter<*> = FavouritePresenterImpl<FavouriteView>(repository, router)

    @Singleton
    @Provides
    fun provideFilterPresenter(repository: NewsRepository, router: Router): FilterPresenter<*> = FilterPresenterImpl<FilterView>(repository, router)

    //provide repository
    @Singleton
    @Provides
    fun provideNewsRepository(localSource: NewsLocalDataSource, remoteSource: NewsRemoteDataSource): NewsRepository = NewsRepository(localSource, remoteSource)

//    @Named("repository")
    @Singleton
    @Provides
    fun provideNewsLocalDataSource(database: NewsDatabase): NewsLocalDataSource = NewsLocalDataSource(database)

//    @Named("apiservice")
    @Singleton
    @Provides
    fun provideNewsRemoteDataSource(apiService: NewsService): NewsRemoteDataSource = NewsRemoteDataSource(apiService)

    //provide database
//    @Named("database")
    @Singleton
    @Provides
    fun provideNewsDatabase(application: Context): NewsDatabase = Room.databaseBuilder(application, NewsDatabase::class.java, DATABASE_NAME).build()

    //provide router
    @Singleton
    @Provides
    fun provideRouter(context: Context): Router = RouterImpl(context)

    //provide adapter
    @Provides
    fun provideNewsAdapter(context: Context): NewsAdapter = NewsAdapter(context)

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
                .client(OkHttpClient.Builder().addInterceptor(ApiKeyInterceptor()).build())
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(URL_API).build()
    }

    //provide apiService
    @Singleton
    @Provides
    fun provideNewsService(retrofit: Retrofit): NewsService  = retrofit.create(NewsService::class.java)
}