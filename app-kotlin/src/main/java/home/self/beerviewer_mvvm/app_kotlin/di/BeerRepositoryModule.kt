package home.self.beerviewer_mvvm.app_kotlin.di


import android.app.Application
import android.arch.persistence.room.Room

import java.util.concurrent.TimeUnit

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import home.self.beerviewer_mvvm.app_kotlin.Constant
import home.self.beerviewer_mvvm.app_kotlin.data.source.BeerDataSource
import home.self.beerviewer_mvvm.app_kotlin.data.source.Local
import home.self.beerviewer_mvvm.app_kotlin.data.source.Remote
import home.self.beerviewer_mvvm.app_kotlin.data.source.local.BeerDao
import home.self.beerviewer_mvvm.app_kotlin.data.source.local.BeerDatabase
import home.self.beerviewer_mvvm.app_kotlin.data.source.local.BeerLocalDataSource
import home.self.beerviewer_mvvm.app_kotlin.data.source.remote.BeerRemoteDataSource
import home.self.beerviewer_mvvm.app_kotlin.network.BeerApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class BeerRepositoryModule {

    @Provides
    @Singleton
    fun provideOkhttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .build()
    }

    @Provides
    @Singleton
    fun retrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
    }

    @Provides
    @Singleton
    fun provideBeerApiService(retrofit: Retrofit): BeerApiService {
        return retrofit.create(BeerApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideBeerDatabase(context: Application): BeerDatabase {
        return Room.databaseBuilder(
                context,
                BeerDatabase::class.java,
                Constant.DB_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
    }

    @Provides
    @Singleton
    fun provideBeerDao(beerDatabase: BeerDatabase): BeerDao {
        return beerDatabase.beerDao()
    }

    @Provides
    @Singleton
    @Remote
    fun provideBeerRemoteDataSource(beerApiService: BeerApiService): BeerDataSource {
        return BeerRemoteDataSource(beerApiService)
    }

    @Provides
    @Singleton
    @Local
    fun provideBeerLocalDataSource(beerDao: BeerDao): BeerDataSource {
        return BeerLocalDataSource(beerDao)
    }

}
