package gaur.himanshu.august.newsapplication.retrofit.responce

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)