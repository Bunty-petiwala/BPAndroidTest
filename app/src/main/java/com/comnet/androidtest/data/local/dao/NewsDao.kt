package com.comnet.androidtest.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.comnet.androidtest.data.model.news.News

@Dao
interface NewsDao {

    /**
     * Insert articles into the database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticles(articles: List<News>): List<Long>

    /**
     * Get all the articles from database
     */
    @Query("SELECT * FROM news_table where type=:topic")
    fun getNewsArticles(topic: String): LiveData<List<News>>

    @Query("DELETE FROM news_table where type=:topic")
    abstract fun deleteAllArticles(topic: String)
}