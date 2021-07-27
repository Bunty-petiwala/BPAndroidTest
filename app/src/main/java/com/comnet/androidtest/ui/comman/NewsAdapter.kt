package com.comnet.androidtest.ui.comman

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.comnet.androidtest.R
import com.comnet.androidtest.data.model.news.News
import com.comnet.androidtest.utils.DateUtils
import kotlinx.android.synthetic.main.row_news_article.view.*


class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsHolder>() {

    /**
     * List of news articles
     */
    private var newsArticles: List<News> = emptyList()


    var onNewsClicked: ((News) -> Unit)? = null

    /**
     * Inflate the view
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        NewsHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_news_article, parent, false))

    /**
     * Bind the view with the data
     */
    override fun onBindViewHolder(newsHolder: NewsHolder, position: Int) =
        newsHolder.bind(newsArticles[position])

    /**
     * Number of items in the list to display
     */
    override fun getItemCount() = newsArticles.size

    /**
     * View Holder Pattern
     */
    inner class NewsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        /**
         * Binds the UI with the data and handles clicks
         */
        fun bind(newsArticle: News) = with(itemView) {
            tvNewsItemTitle.text = newsArticle.title
            tvNewsAuthor.text = newsArticle.author

            tvListItemDateTime.text =
                DateUtils().convertStringDate(newsArticle.publishedAt, DateUtils.FORMAT_DATE_10, DateUtils.FORMAT_DATE_5)

            Glide.with(context)
                .load(newsArticle.urlToImage)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.loading_banner_image)
                        .error(R.drawable.loading_banner_image)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                )
                .into(ivNewsImage)

            itemView.setOnClickListener {
                onNewsClicked?.invoke(newsArticle)
            }

        }
    }

    /**
     * Swap function to set new data on updating
     */
    fun replaceItems(items: List<News>) {
        newsArticles = items
        notifyDataSetChanged()
    }
}