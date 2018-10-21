package home.self.beerviewer_mvvm.app_kotlin.data.source

import home.self.beerviewer_mvvm.app_kotlin.data.model.BeerModel
import home.self.beerviewer_mvvm.app_kotlin.di.qualifier.Local
import home.self.beerviewer_mvvm.app_kotlin.di.qualifier.Remote
import io.reactivex.Flowable
import io.reactivex.Observable
import javax.inject.Inject

internal class BeerRepository @Inject constructor(
//        @field:Remote private val beerRemoteRepositoryApi: BeerRepositoryApi,
//        @field:Local  private val beerLocalRepositoryApi: BeerRepositoryApi

) : BeerRepositoryApi {
    override fun getIndex(): Observable<Int> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getBeers(): Flowable<List<BeerModel>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveBeers(beers: List<BeerModel>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getBeers(pageStart: Int, perPage: Int): Flowable<List<BeerModel>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getBeer(beerId: Int): Flowable<BeerModel> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveBeer(beer: BeerModel) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

//    override fun saveBeer(beer: BeerModel) {
//
//    }
//
//    override fun getBeer(beerId: Int): Flowable<BeerModel> {
//        return beerLocalRepositoryApi
//                .getBeer(beerId)
////                .doOnSubscribe { beerModel ->
////                    if(beerModel == null)
////                        getBeerFromRemote(beerId)
////                    else
////                        Flowable.just<Subscription>(beerModel)
////                }
//    }
//
//    private fun getBeerFromRemote(beerId: Int): Flowable<BeerModel> {
//        return beerRemoteRepositoryApi
//                .getBeer(beerId)
//    }
//    //                    if(beer != null) {
////                        saveBeer(beer)
////                        return@filter beerRemoteRepositoryApi
////                                .getBeer(beerId)
////                                .filter true
////                    } else
////                        return@beerRemoteRepositoryApi
////                                .getBeer(beerId)
////                                .filter false
//
////    override fun saveBeer(beer: BeerModel?) {
////        beerLocalRepositoryApi.saveBeer(beer)
////    }
//
//    override fun saveBeers(beers: List<BeerModel>) {
//        beerLocalRepositoryApi.saveBeers(beers)
//    }
//
//    override fun getBeers(): Flowable<List<BeerModel>> {
//        return Flowable.just(mutableListOf())
////        return beerRemoteRepositoryApi.beers
////                .filter { beers ->
////                    if(!beers.isEmpty()) {
////                        saveBeers(beers)    //save local cache
////                        return@beerRemoteRepositoryApi.getBeers()
////                                .filter true
////                    } else
////                        return@beerRemoteRepositoryApi.getBeers()
////                                .filter false
////                }
//    }
//
//    /**
//     * local cache check
//     * @param pageStart
//     * @param perPage
//     */
//    override fun getBeers(pageStart: Int, perPage: Int): Flowable<List<BeerModel>> {
//        return beerLocalRepositoryApi.getBeers(pageStart, perPage)
////                .switchMap<List<BeerModel>> {                                     //if local is empty, get from remote
////                    beerModels ->
////                    if(beerModels.isEmpty())
////                        return@beerLocalRepositoryApi.getBeers(pageStart, perPage)
////                                .switchMap getBeersFromRemote pageStart, perPage)
////                    else
////                    return@beerLocalRepositoryApi.getBeers(pageStart, perPage)
////                            .switchMap Flowable . just < List < BeerModel > > beerModels
////                }
//                .firstElement()
//                .toFlowable()
//    }
//
//    private fun getBeersFromRemote(pageStart: Int, perPage: Int): Flowable<List<BeerModel>> {
//        return beerRemoteRepositoryApi.getBeers(pageStart, perPage)
////                .filter { beers ->
////                    if(!beers.isEmpty()) {
////                        saveBeers(beers)
////                        return@beerRemoteRepositoryApi.getBeers(pageStart, perPage)
////                                .filter true
////                    } else
////                        return@beerRemoteRepositoryApi.getBeers(pageStart, perPage)
////                                .filter false
////                }
////                .firstElement()
////                .toFlowable()
//    }
//
////    override fun getIndex(): Observable<Int> {
////        return beerLocalRepositoryApi.index
////    }
//
//    companion object {
//
//        private val TAG = BeerRepository::class.java.simpleName
//    }
}
