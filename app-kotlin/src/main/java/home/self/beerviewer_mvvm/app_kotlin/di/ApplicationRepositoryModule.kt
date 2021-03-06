package home.self.beerviewer_mvvm.app_kotlin.di


import android.app.Application
import androidx.room.Room
import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import home.self.beerviewer_mvvm.app_kotlin.base.Constants
import home.self.beerviewer_mvvm.app_kotlin.data.source.local.BeerDao
import home.self.beerviewer_mvvm.app_kotlin.data.source.local.BeerDatabase
import home.self.beerviewer_mvvm.app_kotlin.network.BeerApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module(includes = arrayOf(ApplicationRepositoryModule.ProvideModule::class))
internal interface ApplicationRepositoryModule {

    @Module
    class ProvideModule {
        @Singleton
        @Provides
        fun provideOkhttpClient(): OkHttpClient {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            return OkHttpClient.Builder()
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .writeTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(15, TimeUnit.SECONDS)
                    .addInterceptor(loggingInterceptor)
                    .addNetworkInterceptor(StethoInterceptor())
                    .build()
        }


        @Singleton
        @Provides
        fun retrofit(
                okHttpClient: OkHttpClient
//                retryRxJava2CallAdapterFactory: CallAdapter.Factory

        ): Retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(retryRxJava2CallAdapterFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()


        @Singleton
        @Provides
        fun provideBeerApiService(retrofit: Retrofit): BeerApiService {
            return retrofit.create(BeerApiService::class.java)
        }

        @Singleton
        @Provides
        fun provideBeerDatabase(context: Application): BeerDatabase {
            return Room.databaseBuilder(
                    context,
                    BeerDatabase::class.java,
                    Constants.DB_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
        }

        @Singleton
        @Provides
        fun provideBeerDao(beerDatabase: BeerDatabase): BeerDao {
            return beerDatabase.beerDao()
        }

    }

//    @Binds
//    @Singleton
//    fun bindsRetryingRxJava2CallAdapterFactory(retryRxJava2CallAdapterFactory: RetryingRxJava2CallAdapterFactory): CallAdapter.Factory
}
