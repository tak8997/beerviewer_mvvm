package home.self.beerviewer_mvvm.app_kotlin.data.source


import io.reactivex.Observable

import home.self.beerviewer_mvvm.app_kotlin.data.model.BeerModel
import io.reactivex.Flowable
import io.reactivex.Single

internal interface BeerRepositoryApi {

    fun getIndex(): Observable<Int>

    fun saveBeers(beers: List<BeerModel>)

    fun getBeers(pageStart: Int, perPage: Int): Single<List<BeerModel>>

    fun getBeer(beerId: Int): Flowable<BeerModel>

    fun saveBeer(beer: BeerModel)
}
