package com.comnet.androidtest.data.remote

import androidx.lifecycle.LiveData
import com.comnet.androidtest.data.model.news.NewsSource
import com.comnet.androidtest.utils.network.Resource
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface NewsService {

    @GET("everything")
    fun getNewsSource(@QueryMap options: Map<String, String>): LiveData<Resource<NewsSource>>

}
