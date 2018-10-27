package home.self.beerviewer_mvvm.app_kotlin.data.source.remote


import home.self.beerviewer_mvvm.app_kotlin.data.model.BeerModel
import home.self.beerviewer_mvvm.app_kotlin.data.source.BeerRepositoryApi
import home.self.beerviewer_mvvm.app_kotlin.network.BeerApiService
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class BeerRemoteDataSource @Inject constructor(
        private val apiService: BeerApiService

) : BeerRepositoryApi {

    override fun fetchBeers(pageStart: Int, perPage: Int): Flowable<List<BeerModel>> = Flowable.just(mutableListOf())

    override fun fetchBeersFromRemote(pageStart: Int, perPage: Int): Single<List<BeerModel>> = apiService.fetchBeers(pageStart, perPage)

    override fun fetchBeer(beerId: Int): Flowable<BeerModel> = Flowable.just(BeerModel())

    override fun fetchBeerFromRemote(beerId: Int): Single<BeerModel> = apiService.fetchBeer(beerId)

    override fun saveBeer(beer: BeerModel) {}

    override fun getIndex(): Observable<Int> = Observable.just(1)

    override fun saveBeers(beers: List<BeerModel>) {}

}
