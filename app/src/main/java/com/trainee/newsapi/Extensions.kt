package com.trainee.newsapi

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import com.trainee.newsapi.data.local.entity.News
import com.trainee.newsapi.data.remote.models.ArticlesItem
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

//добавляет фрагмент к активности хосту
inline fun FragmentManager.inTransaction(body: FragmentTransaction.() -> Unit) {
    beginTransaction().apply {
        body()
        commit()
    }
}

fun List<ArticlesItem>.convertToNews(): List<News> {
    val list = ArrayList<News>()
    this.forEach {
        list.add(com.trainee.newsapi.data.local.entity.News(
                ""+SimpleDateFormat(DATE_FORMAT_SERVER, Locale.getDefault()).parse(it.publishedAt).time + it.title,
                it.publishedAt ?: "",
                it.author ?: "Unknown author",
                it.urlToImage ?: "",
                it.description ?: "",
                it.title ?: "",
                it.url ?: "",
                isFavourite = false))
    }
    return list
}

//сравнивает id загруженных новостей из сети с id избранных новостей из БД, возвращает список новостей, которых ещё нет в БД
fun List<com.trainee.newsapi.data.local.entity.News>.compareById(favouritesList: List<News>): List<News> {
    val list = ArrayList<com.trainee.newsapi.data.local.entity.News>()
    val idList = ArrayList<String>()
    favouritesList.forEach {
        idList.add(it.id)
    }
    this.forEach {
        when (idList.contains(it.id)) {
            false -> list.add(it)
        }
    }
    return list
}