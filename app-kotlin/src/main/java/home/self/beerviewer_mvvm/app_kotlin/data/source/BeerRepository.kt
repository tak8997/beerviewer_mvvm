package home.self.beerviewer_mvvm.app_kotlin.data.source

import android.util.Log
import home.self.beerviewer_mvvm.app_kotlin.data.model.BeerModel
import home.self.beerviewer_mvvm.app_kotlin.di.qualifier.Local
import home.self.beerviewer_mvvm.app_kotlin.di.qualifier.Remote
import home.self.beerviewer_mvvm.app_kotlin.extensions.applyFlowableAsysnc
import home.self.beerviewer_mvvm.app_kotlin.extensions.applySingleAsysnc
import home.self.beerviewer_mvvm.app_kotlin.rx.schedulers.BaseSchedulerProvider
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class BeerRepository @Inject constructor(
        @Remote private val remoteRepository: BeerRepositoryApi,
        @Local  private val localRepository: BeerRepositoryApi

) : BeerRepositoryApi {

    companion object {
        private val TAG = BeerRepository::class.java.simpleName
    }

    override fun fetchBeers(pageStart: Int, perPage: Int): Flowable<List<BeerModel>>
            = localRepository
            .fetchBeers(pageStart, perPage)
            .compose(applyFlowableAsysnc())
            .switchMap { beers ->
                if(beers.isEmpty()) {
                    Log.d(TAG, "from_remote")
                    return@switchMap fetchBeersFromRemote(pageStart, perPage).toFlowable()
                }

                return@switchMap Flowable.just(beers)
            }

    override fun fetchBeersFromRemote(pageStart: Int, perPage: Int): Single<List<BeerModel>>
            = remoteRepository
            .fetchBeersFromRemote(pageStart, perPage)
            .compose(applySingleAsysnc())
            .doAfterSuccess { beers ->
                saveBeers(beers)
            }

    override fun fetchBeer(beerId: Int): Flowable<BeerModel>
            = localRepository
            .fetchBeer(beerId)
            .compose(applyFlowableAsysnc())
            .doOnSubscribe { beer ->
                if(beer == null) {
                    fetchBeerFromRemote(beerId)
                } else {
                    Flowable.just(beer)
                }
            }

    override fun fetchBeerFromRemote(beerId: Int): Single<BeerModel>
            = remoteRepository
            .fetchBeerFromRemote(beerId)
            .compose(applySingleAsysnc())
            .doAfterSuccess { beer ->
                saveBeer(beer)
            }

    override fun saveBeers(beers: List<BeerModel>) = localRepository.saveBeers(beers)

    override fun saveBeer(beer: BeerModel) = localRepository.saveBeer(beer)

    override fun getIndex(): Observable<Int> = localRepository.getIndex()

}
