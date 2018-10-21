package home.self.beerviewer_mvvm.app_kotlin.data.source.local

import javax.inject.Inject
import javax.inject.Singleton

import home.self.beerviewer_mvvm.app_kotlin.data.model.BeerModel
import home.self.beerviewer_mvvm.app_kotlin.data.source.BeerRepositoryApi
import home.self.beerviewer_mvvm.app_kotlin.rx.rxbus.Events
import home.self.beerviewer_mvvm.app_kotlin.rx.rxbus.RxEventBus
import home.self.beerviewer_mvvm.app_kotlin.util.IndexUtil
import io.reactivex.Flowable
import io.reactivex.Observable

@Singleton
internal class BeerLocalDataSource @Inject constructor(private val beerDao: BeerDao) : BeerRepositoryApi {
    override fun getIndex(): Observable<Int> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getBeers(): Flowable<List<BeerModel>> {
        return Flowable.just(mutableListOf())
    }

//    override val index = BehaviorSubject.create<Int>()

    private fun sendEventBus() {
        RxEventBus.getInstance().post(Events.PageEvent())
    }

    override fun saveBeers(beers: List<BeerModel>) {
        val previous = beerDao.allBeers

        beerDao.deleteBeers(previous)
        beerDao.insertBeers(beers)
    }

    override fun saveBeer(beer: BeerModel) {
        beerDao.insertBeer(beer)
    }

//    override fun getBeers(): Flowable<List<BeerModel>>? {
//        return null
//    }
//
//    override fun getIndex(): io.reactivex.Observable<Int> {
//        return index
//    }

    override fun getBeers(pageStart: Int, perPage: Int): Flowable<List<BeerModel>> {
        val indexStart = IndexUtil.getIndex(pageStart)

//        if(pageStart == 10)
//            index.onNext(1)

        return beerDao.getBeers(indexStart, perPage)
    }

    override fun getBeer(beerId: Int): Flowable<BeerModel> {
        return beerDao.getBeer(beerId)
    }


}













