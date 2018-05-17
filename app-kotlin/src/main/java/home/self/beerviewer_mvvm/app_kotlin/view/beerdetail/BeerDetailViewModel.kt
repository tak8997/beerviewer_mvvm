package home.self.beerviewer_mvvm.app_kotlin.view.beerdetail

import android.arch.lifecycle.ViewModel
import home.self.beerviewer_mvvm.app_kotlin.data.model.BeerModel
import home.self.beerviewer_mvvm.app_kotlin.data.source.BeerRepository
import home.self.beerviewer_mvvm.app_kotlin.rx.schedulers.BaseSchedulerProvider
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject

/**
 * Created by Tak on 2018. 5. 16..
 */

class BeerDetailViewModel(val repository: BeerRepository,
                          val schedulerProvider: BaseSchedulerProvider,
                          val beerId : Int) : ViewModel() {

    val isLoading: BehaviorSubject<Boolean> = BehaviorSubject.create()

    val message: BehaviorSubject<String> = BehaviorSubject.create()

    val beer: BehaviorSubject<BeerModel> = BehaviorSubject.create()

    val beerInfo: BehaviorSubject<String> = BehaviorSubject.create()

    fun getBeer(): Disposable {
        return if (beerId != -1) {
            repository
                    .getBeer(beerId)
                    .doOnSubscribe { isLoading.onNext(true) }
                    .doOnTerminate { isLoading.onNext(false) }
                    .subscribeOn(schedulerProvider.io())
                    .subscribe({ item ->
                        beer.onNext(item)
                    }) { message.onNext(it.message ?: "unexpected error") }
        } else {
            message.onNext("not found")
            throw Throwable("not found")
        }
    }




}
