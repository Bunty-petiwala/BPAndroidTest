package com.comnet.androidtest.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.comnet.androidtest.data.local.dao.NewsDao
import com.comnet.androidtest.data.model.news.News

@Database(entities = [News::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun newsArticlesDao(): NewsDao
}