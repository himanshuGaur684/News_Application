package gaur.himanshu.august.newsapplication.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ArticleRemoteKey(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val prev: Int?,
    val next: Int?
)