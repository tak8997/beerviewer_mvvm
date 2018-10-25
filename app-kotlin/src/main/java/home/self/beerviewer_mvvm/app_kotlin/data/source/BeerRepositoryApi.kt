package home.self.beerviewer_mvvm.app_kotlin.data.source


import home.self.beerviewer_mvvm.app_kotlin.data.model.BeerModel
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

internal interface BeerRepositoryApi {

    fun getIndex(): Single<Int>

    fun saveBeers(beers: List<BeerModel>)

    fun fetchBeers(pageStart: Int, perPage: Int): Flowable<List<BeerModel>>

    fun fetchBeersFromRemote(pageStart: Int, perPage: Int): Single<List<BeerModel>>

    fun getBeer(beerId: Int): Flowable<BeerModel>

    fun saveBeer(beer: BeerModel)
}
