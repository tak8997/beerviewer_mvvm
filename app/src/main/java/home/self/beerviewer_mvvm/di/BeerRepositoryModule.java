package home.self.beerviewer_mvvm.di;


import android.app.Application;
import androidx.room.Room;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import home.self.beerviewer_mvvm.Constant;
import home.self.beerviewer_mvvm.data.source.BeerDataSource;
import home.self.beerviewer_mvvm.data.source.Local;
import home.self.beerviewer_mvvm.data.source.Remote;
import home.self.beerviewer_mvvm.data.source.local.BeerDao;
import home.self.beerviewer_mvvm.data.source.local.BeerDatabase;
import home.self.beerviewer_mvvm.data.source.local.BeerLocalDataSource;
import home.self.beerviewer_mvvm.data.source.remote.BeerRemoteDataSource;
import home.self.beerviewer_mvvm.network.BeerApiService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class BeerRepositoryModule {

    @Provides
    @Singleton
    OkHttpClient provideOkhttpClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .build();
    }

    @Provides
    @Singleton
    Retrofit retrofit(OkHttpClient okHttpClient) {
        return new Retrofit
                .Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    BeerApiService provideBeerApiService(Retrofit retrofit) {
        return retrofit.create(BeerApiService.class);
    }

    @Provides
    @Singleton
    BeerDatabase provideBeerDatabase(Application context) {
        return Room.databaseBuilder(
                context,
                BeerDatabase.class,
                Constant.DB_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @Singleton
    BeerDao provideBeerDao(BeerDatabase beerDatabase) {
        return beerDatabase.beerDao();
    }

    @Provides
    @Singleton
    @Remote
    BeerDataSource provideBeerRemoteDataSource(BeerApiService beerApiService) {
        return new BeerRemoteDataSource(beerApiService);
    }

    @Provides
    @Singleton
    @Local
    BeerDataSource provideBeerLocalDataSource(BeerDao beerDao) {
        return new BeerLocalDataSource(beerDao);
    }

}
