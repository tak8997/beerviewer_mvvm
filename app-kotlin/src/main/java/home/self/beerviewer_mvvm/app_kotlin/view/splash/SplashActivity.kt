package home.self.beerviewer_mvvm.app_kotlin.view.splash

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import dagger.android.support.DaggerAppCompatActivity
import home.self.beerviewer_mvvm.app_kotlin.R
import home.self.beerviewer_mvvm.app_kotlin.extensions.AutoClearedDisposable
import home.self.beerviewer_mvvm.app_kotlin.rx.schedulers.BaseSchedulerProvider
import home.self.beerviewer_mvvm.app_kotlin.view.beersview.BeersViewActivity
import org.jetbrains.anko.longToast
import org.jetbrains.anko.startActivity
import javax.inject.Inject

class SplashActivity : DaggerAppCompatActivity() {

    private val disposables = AutoClearedDisposable(this)

    @Inject lateinit var viewModelFactory : SplashViewModelFactory
    @Inject lateinit var schedulerProvider: BaseSchedulerProvider

    lateinit var viewModel : SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        viewModel = ViewModelProviders.of(
                this, viewModelFactory)[SplashViewModel::class.java]

        lifecycle.addObserver(disposables)

        disposables.add(viewModel.getBeers())

        disposables.add(viewModel.message
                .observeOn(schedulerProvider.ui())
                .subscribe {message ->
                    longToast(message)
                })

        disposables.add(viewModel.isSplashLoading
                .observeOn(schedulerProvider.ui())
                .subscribe {it ->
                    if (it == false)
                        startActivity<BeersViewActivity>()
                })
    }


}
