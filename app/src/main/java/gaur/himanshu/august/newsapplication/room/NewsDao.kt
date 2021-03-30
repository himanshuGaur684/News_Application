package gaur.himanshu.august.newsapplication.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import gaur.himanshu.august.newsapplication.retrofit.responce.Article

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(list: List<Article>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticleSingle(article: Article)

    @Query("SELECT * FROM Article ")
    fun getAllArticles(): PagingSource<Int, Article>

    @Query("DELETE FROM Article")
    suspend fun deleteAllArticles()


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRemoteKeys(list: List<ArticleRemoteKey>)


    @Query("SELECT * FROM ArticleRemoteKey WHERE id = :id")
    suspend fun getAllREmoteKey(id: String): ArticleRemoteKey?

    @Query("DELETE FROM ArticleRemoteKey")
    suspend fun deleteAllRemoteKeys()

}