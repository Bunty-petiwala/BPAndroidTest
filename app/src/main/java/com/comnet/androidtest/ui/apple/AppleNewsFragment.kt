package com.comnet.androidtest.ui.apple

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.comnet.androidtest.databinding.FragmentAppleNewsBinding
import com.comnet.androidtest.ui.comman.NewsAdapter
import com.comnet.androidtest.ui.comman.NewsArticlesViewModel
import com.comnet.androidtest.utils.DateUtils
import com.comnet.androidtest.utils.extensions.load
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.empty_layout_news_article.*
import kotlinx.android.synthetic.main.progress_layout_news_article.*
import java.util.*

@AndroidEntryPoint
class AppleNewsFragment : Fragment() {

    private lateinit var mViewModel: NewsArticlesViewModel
    private var _binding: FragmentAppleNewsBinding? = null

    private val binding get() = _binding!!

    private lateinit var adapter: NewsAdapter
    private lateinit var mCalendar: Calendar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mViewModel =
            ViewModelProvider(this).get(NewsArticlesViewModel::class.java)
        _binding = FragmentAppleNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mCalendar = Calendar.getInstance()

        binding.newsList.setEmptyView(empty_view)
        binding.newsList.setProgressView(progress_view)

        adapter = NewsAdapter()
        adapter.onNewsClicked = {
            //TODO Your news item click invoked here
        }

        binding.newsList.adapter = adapter
        binding.newsList.layoutManager = LinearLayoutManager(requireContext())

        getNews("apple")

        binding.swipe.setOnRefreshListener {
            getNews("apple")
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    /**
     * Get  news using Network & DB Bound Resource
     * Observing for data change from DB and Network Both
     */
    private fun getNews(query: String) {
        mViewModel.getNewsArticles(query=query,
            to = DateUtils().getYesterdayDate(mCalendar), from = "").observe(viewLifecycleOwner, Observer {
            when {
                it.status.isLoading() -> {
                    binding.swipe.isRefreshing = false
                    binding.newsList.showProgressView()
                }
                it.status.isSuccessful() -> {
                    binding.swipe.isRefreshing = false
                    it?.load(binding.newsList) { news ->
                        adapter.replaceItems(news!!)
                    }
                }
                it.status.isError() -> {
                    binding.swipe.isRefreshing = false
                    if (it.errorMessage != null)
                        Toast.makeText(requireContext(), ""+it.errorMessage.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        })
    }


}