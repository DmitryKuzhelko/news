package com.trainee.newsapi.data.local.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull

@Entity(tableName = "news")
data class News(@PrimaryKey @NonNull val id: String,
                val publishedAt: String,
                val author: String,
                val urlToImage: String,
                val description: String,
                val title: String,
                val url: String,
                var isFavourite: Boolean
)