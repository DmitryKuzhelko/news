package com.trainee.newsapi.data.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.trainee.newsapi.data.local.entity.News

@Database(entities = [News::class], version = 1)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}