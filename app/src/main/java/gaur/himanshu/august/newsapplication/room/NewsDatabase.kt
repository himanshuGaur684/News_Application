package gaur.himanshu.august.newsapplication.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import gaur.himanshu.august.newsapplication.RoomTypeConvertor
import gaur.himanshu.august.newsapplication.retrofit.responce.Article

@Database(entities = [Article::class, ArticleRemoteKey::class], version = 1)
@TypeConverters(RoomTypeConvertor::class)
abstract class NewsDatabase : RoomDatabase() {

    companion object {
        fun getInstance(context: Context): NewsDatabase {
            return Room.databaseBuilder(context, NewsDatabase::class.java, "name").build()
        }
    }

    abstract fun getNewsDao(): NewsDao

}