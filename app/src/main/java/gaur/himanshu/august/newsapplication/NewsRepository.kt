package gaur.himanshu.august.newsapplication

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import gaur.himanshu.august.newsapplication.paging.NewsPagingSource
import gaur.himanshu.august.newsapplication.retrofit.NewsInterface
import gaur.himanshu.august.newsapplication.retrofit.responce.Article

class NewsRepository(val newsInterface: NewsInterface) {


    fun getAllNewsStream(): LiveData<PagingData<Article>> = Pager(
        config = PagingConfig(
            20,
            5,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            NewsPagingSource(newsInterface)
        }
    ).liveData

}