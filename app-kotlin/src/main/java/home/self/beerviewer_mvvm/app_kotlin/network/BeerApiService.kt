package home.self.beerviewer_mvvm.app_kotlin.network

import home.self.beerviewer_mvvm.app_kotlin.data.model.BeerModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Tak on 2018. 1. 27..
 */

interface BeerApiService {

    @GET("beers/")
    fun fetchBeers(@Query("page") page: Int, @Query("per_page") perPage: Int): Single<List<BeerModel>>

    @GET("beers/{beer_id}")
    fun fetchBeer(@Path("beer_id") beerId: Int): Single<BeerModel>

}
