package com.trainee.newsapi.screens.news.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.trainee.newsapi.*

class HostNewsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.host_news_activity)

        //если в интенте ничего не пришло(например при первом запуске), то добавляем фрагмент и в аргументы придут пустые строки по дефолту,
        // иначе(ситуация, когда пришли данные после закрытия экрана Filter) передаём параметры в аргументы фрагмента.
        when (intent.extras) {
            null -> {
                supportFragmentManager.inTransaction {
                    replace(R.id.news_fragment_container, NewsFragment.newInstance(), NEWS_TAG)
                }
            }
            else -> {
                val sort = intent.getStringExtra(SORT_BY)
                val from = intent.getStringExtra(FROM_DATE)
                val to = intent.getStringExtra(TO_DATE)

                supportFragmentManager.inTransaction {
                    replace(R.id.news_fragment_container, NewsFragment.newInstance(sort, from, to), NEWS_TAG)
                }
            }
        }
    }
}