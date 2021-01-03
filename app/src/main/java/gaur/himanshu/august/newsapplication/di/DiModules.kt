package gaur.himanshu.august.newsapplication.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import gaur.himanshu.august.newsapplication.NewsRepository
import gaur.himanshu.august.newsapplication.retrofit.NewsInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DiModules {


    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Singleton
    @Provides
    fun provideNewsInterface(retrofit: Retrofit): NewsInterface {
        return retrofit.create(NewsInterface::class.java)
    }


    @Singleton
    @Provides
    fun provideRepository(newsInterface: NewsInterface): NewsRepository {
        return NewsRepository(newsInterface)
    }

}