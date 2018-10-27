package home.self.beerviewer_mvvm.app_kotlin.view.beersview

import android.arch.lifecycle.MutableLiveData
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection
import home.self.beerviewer_mvvm.app_kotlin.BaseViewModel
import home.self.beerviewer_mvvm.app_kotlin.Constants
import home.self.beerviewer_mvvm.app_kotlin.data.model.BeerModel
import home.self.beerviewer_mvvm.app_kotlin.data.source.BeerRepositoryApi
import home.self.beerviewer_mvvm.app_kotlin.di.qualifier.App
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

internal interface BeersViewModel {

    interface Inputs {
        fun fetchBeers(pageStart: Int, perPage: Int, direction: SwipyRefreshLayoutDirection): Disposable
    }

    interface Outputs {
        fun fetchBeers(): MutableLiveData<List<BeerModel>>
        fun fetchIndex(): MutableLiveData<Int>
        fun isLoading(): MutableLiveData<Boolean>
    }

    class ViewModel @Inject constructor(
            @App private val repository: BeerRepositoryApi

    ): BaseViewModel(), Inputs, Outputs {

        val intpus = this
        val outpus = this

        private val beers = MutableLiveData<List<BeerModel>>()
        private val isLoading = MutableLiveData<Boolean>()
        private val index = MutableLiveData<Int>()

        init {
            intent().map { it.getSerializableExtra(Constants.EXTRA_DEFAULT_PAGE) as Pair<Int, Int> }
                    .doOnNext { isLoading.postValue(true) }
                    .subscribeBy { it ->
                        fetchBeers(it.first, it.second, SwipyRefreshLayoutDirection.TOP)
                    }

            subscribeIndex()
        }

        override fun fetchBeers(pageStart: Int, perPage: Int, direction: SwipyRefreshLayoutDirection): Disposable
                = repository
                .fetchBeers(pageStart, perPage)
                .doOnNext { isLoading.postValue(false) }
                .flatMap { beers ->
                    Flowable.fromIterable(beers)
                            .doOnNext { beer -> beer.direction = direction }
                            .toList()
                            .toFlowable()
                }
                .subscribeBy { beerList ->
                    beers.postValue(beerList)
                }

        override fun fetchBeers(): MutableLiveData<List<BeerModel>> = beers

        override fun fetchIndex(): MutableLiveData<Int> = index

        override fun isLoading(): MutableLiveData<Boolean> = isLoading

        private fun subscribeIndex()
                = repository
                .getIndex()
                .subscribe { it ->
                    index.postValue(it)
                }
    }

}

