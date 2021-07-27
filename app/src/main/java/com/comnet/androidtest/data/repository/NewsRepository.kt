package com.comnet.androidtest.data.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.comnet.androidtest.BuildConfig
import com.comnet.androidtest.application.AppExecutors
import com.comnet.androidtest.data.local.dao.NewsDao
import com.comnet.androidtest.data.model.news.News
import com.comnet.androidtest.data.model.news.NewsSource
import com.comnet.androidtest.data.remote.NewsService
import com.comnet.androidtest.utils.ConnectivityUtil
import com.comnet.androidtest.utils.network.NetworkAndDBBoundResource
import com.comnet.androidtest.utils.network.NetworkResource
import com.comnet.androidtest.utils.network.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class NewsRepository @Inject constructor(
    private val newsDao: NewsDao,
    private val apiServices: NewsService,
    @ApplicationContext val context: Context,
    private val appExecutors: AppExecutors = AppExecutors()
) {

    /**
     * Fetch the news articles from database if exist else fetch from web
     * and persist them in the database
     */
    fun getNewsArticles(queryShortKey: String,
                        domain: String = "",
                        from: String = "",
                        to: String = ""): LiveData<Resource<List<News>?>> {

        var articleTopic: String = ""

        val data = HashMap<String, String>()
        if(queryShortKey != "") {
            data["q"] = queryShortKey
            articleTopic = queryShortKey
        }
        data["apiKey"] = BuildConfig.NEWS_API_KEY
        if(domain != "") {
            data["domains"] = domain
            articleTopic = domain
        }
        if(from != "")
            data["from"] = from
        if(to != "")
            data["to"] = to

        Log.e("TAG", "getNewsArticles: $data", )

        return object : NetworkAndDBBoundResource<List<News>, NewsSource>(appExecutors) {
            override fun saveCallResult(item: NewsSource) {
                if (item.articles.isNotEmpty()) {
                    newsDao.deleteAllArticles(articleTopic)
                    for ((index, article) in item.articles.withIndex()){
                        item.articles[index].type = articleTopic
                    }
                    newsDao.insertArticles(item.articles)
                }
            }

            override fun shouldFetch(data: List<News>?) =
                (ConnectivityUtil.isConnected(context))

            override fun loadFromDb() = newsDao.getNewsArticles(articleTopic)

            override fun createCall() =
                apiServices.getNewsSource(data)

        }.asLiveData()
    }

    /**
     * Fetch the news articles from database if exist else fetch from web
     * and persist them in the database
     * LiveData<Resource<NewsSource>>
     */
    fun getNewsArticlesFromServerOnly(query: String,
                                      domain: String = "",
                                      from: String = "",
                                      to: String = ""):
            LiveData<Resource<NewsSource>> {

        val data = HashMap<String, String>()
        if(query != "")
            data["q"] = query
        data["apiKey"] = BuildConfig.NEWS_API_KEY
        if(domain != "")
            data["domains"] = domain
        if(from != "")
            data["from"] = from
        if(to != "")
            data["to"] = to

        return object : NetworkResource<NewsSource>() {
            override fun createCall(): LiveData<Resource<NewsSource>> {
                return apiServices.getNewsSource(data)
            }

        }.asLiveData()
    }

}