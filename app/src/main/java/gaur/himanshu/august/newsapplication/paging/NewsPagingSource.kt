package gaur.himanshu.august.newsapplication.paging

import androidx.paging.PagingSource
import gaur.himanshu.august.newsapplication.Constants
import gaur.himanshu.august.newsapplication.retrofit.NewsInterface
import gaur.himanshu.august.newsapplication.retrofit.responce.Article
import retrofit2.HttpException
import java.io.IOException

const val STARTING_INDEX = 1

class NewsPagingSource(val newsInterface: NewsInterface) : PagingSource<Int, Article>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {

        val position = params.key ?: STARTING_INDEX

        return try {
            val data = newsInterface.getAllNews(
                "in",
                "business",
                Constants.API_KEY,
                position,
                params.loadSize
            )
            LoadResult.Page(
                data = data.articles,
                prevKey = if (params.key == STARTING_INDEX) null else position - 1,
                nextKey = if (data.articles.isEmpty()) null else position + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }


    }
}