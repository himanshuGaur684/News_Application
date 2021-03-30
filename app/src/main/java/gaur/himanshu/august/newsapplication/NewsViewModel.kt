package gaur.himanshu.august.newsapplication

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import gaur.himanshu.august.newsapplication.paging.NewsRemoteMediator
import gaur.himanshu.august.newsapplication.retrofit.NewsInterface
import gaur.himanshu.august.newsapplication.retrofit.responce.Article
import gaur.himanshu.august.newsapplication.room.NewsDao

class NewsViewModel @ViewModelInject constructor(
    private val newsRepository: NewsRepository, private val newsDao: NewsDao,
    private val newsInterface: NewsInterface
) : ViewModel() {

    val list: LiveData<PagingData<Article>> = newsRepository.getAllNewsStream()


    @ExperimentalPagingApi
    val pager = Pager(
        PagingConfig(pageSize = 10),
        remoteMediator = NewsRemoteMediator(newsDao, newsInterface, 1)
    ) {
        newsDao.getAllArticles()
    }.flow
}