package home.self.beerviewer_mvvm.app_kotlin.data.source.local

import home.self.beerviewer_mvvm.app_kotlin.data.model.BeerModel
import home.self.beerviewer_mvvm.app_kotlin.data.source.BeerRepositoryApi
import home.self.beerviewer_mvvm.app_kotlin.util.IndexUtil
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class BeerLocalDataSource @Inject constructor(
        private val beerDao: BeerDao

) : BeerRepositoryApi {

    private val index = BehaviorSubject.create<Int>()

    override fun getIndex(): Single<Int> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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

    override fun fetchBeersFromRemote(pageStart: Int, perPage: Int): Single<List<BeerModel>> = Single.just(mutableListOf())

    override fun fetchBeers(pageStart: Int, perPage: Int): Flowable<List<BeerModel>> {
        val indexStart: Int

        if(pageStart == 10) {
            indexStart = IndexUtil.getIndex(pageStart)

            index.onNext(1)
        } else
            indexStart = IndexUtil.getIndex(pageStart)

        return beerDao.getBeers(indexStart, perPage)
    }

    override fun getBeer(beerId: Int): Flowable<BeerModel> {
        return beerDao.getBeer(beerId)
    }

}













