package gaur.himanshu.august.newsapplication

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import gaur.himanshu.august.newsapplication.retrofit.responce.Article

class NewsViewModel @ViewModelInject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    val list: LiveData<PagingData<Article>> = newsRepository.getAllNewsStream()

}