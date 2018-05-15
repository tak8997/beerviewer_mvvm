package home.self.beerviewer_mvvm.app_kotlin.network;

import java.util.List;

import home.self.beerviewer_mvvm.data.model.BeerModel;
import io.reactivex.Maybe;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Tak on 2018. 1. 27..
 */

public interface BeerApiService {

    @GET("beers/")
    Maybe<List<BeerModel>> getBeers();

    @GET("beers/")
    Single<List<BeerModel>> getBeers(@Query("page") int page, @Query("per_page") int perPage);

//    @GET("beers/{beer_id}")
//    Call<List<BeerModel>> getBeer(@Path("beer_id") int beerId);

}
