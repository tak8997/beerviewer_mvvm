package home.self.beerviewer_mvvm.app_kotlin.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Tak on 2018. 1. 27..
 */

public class BeerViewerClient {

    private static final String BASE_URL = "http://api.punkapi.com/v2/";

    private static BeerViewerClient instance;
    private Retrofit retrofit;

    public static <S> S createService(Class<S> service) {
        if (instance == null) {
            instance = new BeerViewerClient();
        }

        return instance.getService(service);
    }

    public BeerViewerClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private <S> S getService(Class<S> service) {
        return retrofit.create(service);
    }

}
