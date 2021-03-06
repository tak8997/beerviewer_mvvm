package home.self.beerviewer_mvvm.network;

import java.util.List;

import home.self.beerviewer_mvvm.data.model.BeerModel;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Tak on 2018. 1. 27..
 */

public interface BeerApiService {

    @GET("beers/")
    Maybe<List<BeerModel>> getBeers();

    @GET("beers/")
    Flowable<List<BeerModel>> getBeers(@Query("page") int page, @Query("per_page") int perPage);

    @GET("beers/{beer_id}")
    Single<BeerModel> getBeer(@Path("beer_id") int beerId);

//    @GET("beers/{beer_id}")
//    Call<List<BeerModel>> getBeer(@Path("beer_id") int beerId);

}
