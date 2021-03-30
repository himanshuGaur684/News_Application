package gaur.himanshu.august.newsapplication.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import gaur.himanshu.august.newsapplication.Constants
import gaur.himanshu.august.newsapplication.retrofit.NewsInterface
import gaur.himanshu.august.newsapplication.retrofit.responce.Article
import gaur.himanshu.august.newsapplication.room.ArticleRemoteKey
import gaur.himanshu.august.newsapplication.room.NewsDao
import java.io.InvalidObjectException

@ExperimentalPagingApi
class NewsRemoteMediator(
    private val newsDao: NewsDao,
    private val newsInterface: NewsInterface,
    private val initialPage: Int = 1
) : RemoteMediator<Int, Article>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Article>
    ): MediatorResult {

        return try {

            // Judging the page number
            val page = when (loadType) {
                LoadType.APPEND -> {

                    val remoteKey =
                        getLastRemoteKey(state) ?: throw InvalidObjectException("Inafjklg")
                    remoteKey.next ?: return MediatorResult.Success(endOfPaginationReached = true)

                }
                LoadType.PREPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                LoadType.REFRESH -> {
                    val remoteKey = getClosestRemoteKeys(state)
                    remoteKey?.next?.minus(1) ?: initialPage
                }
            }

            // make network request
            val response = newsInterface.getAllSportsNews(
                "in",
                "sports",
                Constants.API_KEY,
                page,
                state.config.pageSize
            )
            val endOfPagination = response.body()?.articles?.size!! < state.config.pageSize

            if (response.isSuccessful) {

                response.body()?.let {

                    // flush our data
                    if (loadType == LoadType.REFRESH) {
                        newsDao.deleteAllArticles()
                        newsDao.deleteAllRemoteKeys()
                    }


                    val prev = if (page == initialPage) null else page - 1
                    val next = if (endOfPagination) null else page + 1


                    val list = response.body()?.articles?.map {
                        ArticleRemoteKey(it.title, prev, next)
                    }


                    // make list of remote keys

                    if (list != null) {
                        newsDao.insertAllRemoteKeys(list)
                    }

                    // insert to the room
                    newsDao.insertArticles(it.articles)


                }
                MediatorResult.Success(endOfPagination)
            } else {
                MediatorResult.Success(endOfPaginationReached = true)
            }


        } catch (e: Exception) {
            MediatorResult.Error(e)
        }

    }

    private suspend fun getClosestRemoteKeys(state: PagingState<Int, Article>): ArticleRemoteKey? {

        return state.anchorPosition?.let {
            state.closestItemToPosition(it)?.let {
                newsDao.getAllREmoteKey(it.title)
            }
        }

    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, Article>): ArticleRemoteKey? {
        return state.lastItemOrNull()?.let {
            newsDao.getAllREmoteKey(it.title)
        }
    }

}