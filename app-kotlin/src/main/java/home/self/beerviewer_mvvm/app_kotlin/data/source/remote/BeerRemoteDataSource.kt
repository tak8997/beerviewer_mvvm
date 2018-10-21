package home.self.beerviewer_mvvm.app_kotlin.data.source.remote


import javax.inject.Inject
import javax.inject.Singleton

import home.self.beerviewer_mvvm.app_kotlin.data.model.BeerModel
import home.self.beerviewer_mvvm.app_kotlin.data.source.BeerRepositoryApi
import home.self.beerviewer_mvvm.app_kotlin.network.BeerApiService
import io.reactivex.Flowable
import io.reactivex.Observable

@Singleton
internal class BeerRemoteDataSource @Inject constructor(private val apiService: BeerApiService) : BeerRepositoryApi {
    override fun getIndex(): Observable<Int> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getBeers(): Flowable<List<BeerModel>> {
        return Flowable.just(mutableListOf())
    }

    override fun saveBeers(beers: List<BeerModel>) {}

//    override fun getBeers(): Flowable<List<BeerModel>> {
//        return apiService.beers
//    }

    override fun getBeers(pageStart: Int, perPage: Int): Flowable<List<BeerModel>> {
        return apiService.getBeers(pageStart, perPage)
    }

    override fun getBeer(beerId: Int): Flowable<BeerModel> {
        return apiService.getBeer(beerId).toFlowable()
    }

    override fun saveBeer(beer: BeerModel) {}

//    override fun getIndex(): Observable<Int>? {
//        return null
//    }
}
