package home.self.beerviewer_mvvm.app_kotlin.data.source


import io.reactivex.Observable

import home.self.beerviewer_mvvm.app_kotlin.data.model.BeerModel
import io.reactivex.Flowable

internal interface BeerRepositoryApi {

    fun getBeers(): Flowable<List<BeerModel>>
//
    fun getIndex(): Observable<Int>

    fun saveBeers(beers: List<BeerModel>)

    fun getBeers(pageStart: Int, perPage: Int): Flowable<List<BeerModel>>

    fun getBeer(beerId: Int): Flowable<BeerModel>

    fun saveBeer(beer: BeerModel)
}
