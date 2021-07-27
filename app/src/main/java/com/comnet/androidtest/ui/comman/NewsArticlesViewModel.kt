package com.comnet.androidtest.ui.comman

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.comnet.androidtest.data.model.news.News
import com.comnet.androidtest.data.repository.NewsRepository
import com.comnet.androidtest.utils.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsArticlesViewModel @Inject constructor(private val newsRepository: NewsRepository) : ViewModel() {

    /**
     * Loading news articles from internet and database
     */
    private fun newsArticles(query: String= "" , to: String= "", domain: String = "", from: String = ""): LiveData<Resource<List<News>?>> =
        newsRepository.getNewsArticles(queryShortKey = query, to = to, domain = domain,  from =  from)


    fun getNewsArticles(query: String = "", to: String = "", domain: String = "",
                        from: String = "") = newsArticles(query = query, to = to, from = from, domain =  domain)

    /**
     * Loading news articles from internet only
     */
    private fun newsArticlesFromOnlyServer(query: String) =
        newsRepository.getNewsArticlesFromServerOnly(query)

    fun getNewsArticlesFromServer(queryString: String) = newsArticlesFromOnlyServer(queryString)

}