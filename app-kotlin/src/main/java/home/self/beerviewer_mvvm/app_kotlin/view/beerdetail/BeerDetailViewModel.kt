package home.self.beerviewer_mvvm.app_kotlin.view.beerdetail

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import home.self.beerviewer_mvvm.app_kotlin.BaseViewModel
import home.self.beerviewer_mvvm.app_kotlin.Constants
import home.self.beerviewer_mvvm.app_kotlin.data.model.BeerModel
import home.self.beerviewer_mvvm.app_kotlin.data.source.BeerRepositoryApi
import home.self.beerviewer_mvvm.app_kotlin.di.qualifier.App
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

/**
 * Created by Tak on 2018. 5. 16..
 */

internal interface BeerDetailViewModel {

    interface Inputs {
        fun fetchBeerInfo(): LiveData<String>
    }

    interface Outputs {
        fun fetchBeer(): MutableLiveData<BeerModel>
        fun message(): MutableLiveData<String>
        fun isLoading(): MutableLiveData<Boolean>
    }

    class ViewModel @Inject constructor(
            @App val repository: BeerRepositoryApi

    ) : BaseViewModel(), Inputs, Outputs {

        val inputs = this
        private val beerInfo: LiveData<String>

        val outputs = this
        private val beer = MutableLiveData<BeerModel>()
        private val isLoading = MutableLiveData<Boolean>()
        private val message = MutableLiveData<String>()

        init {
            intent().map { it.getIntExtra(Constants.KEY_BEAR_ID, -1) }
                    .filter { beerId -> beerId != -1 }
                    .subscribeBy { beerId ->
                        fetchBeer(beerId)
                    }

            beerInfo = Transformations.map(beer) { beer ->
                beer.name + ",\n" + beer.brewersTips + ",\n" + beer.description
            }
        }

        override fun fetchBeer(): MutableLiveData<BeerModel> = beer

        override fun message(): MutableLiveData<String> = message

        override fun isLoading(): MutableLiveData<Boolean> = isLoading

        override fun fetchBeerInfo() = beerInfo

        private fun fetchBeer(beerId: Int): Disposable
                = repository
                .fetchBeer(beerId)
                .doOnSubscribe { isLoading.postValue(true) }
                .doOnTerminate { isLoading.postValue(false) }
                .doOnError { message.postValue(it.message ?: "unexpected error") }
                .subscribeBy { beerItem ->
                    beer.postValue(beerItem)
                }

    }
}
