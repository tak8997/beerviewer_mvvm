package home.self.beerviewer_mvvm.app_kotlin.view.splash

import android.os.Bundle
import com.bumptech.glide.Glide
import home.self.beerviewer_mvvm.app_kotlin.BaseActivity
import home.self.beerviewer_mvvm.app_kotlin.R
import home.self.beerviewer_mvvm.app_kotlin.rx.lifecycle.AutoClearedDisposable
import home.self.beerviewer_mvvm.app_kotlin.rx.schedulers.BaseSchedulerProvider
import home.self.beerviewer_mvvm.app_kotlin.view.beersview.BeersViewActivity
import kotlinx.android.synthetic.main.activity_splash.*
import org.jetbrains.anko.longToast
import org.jetbrains.anko.startActivity
import javax.inject.Inject

internal class SplashActivity : BaseActivity<SplashViewModel.ViewModel>() {

    @Inject lateinit var schedulerProvider: BaseSchedulerProvider

    private val disposables = AutoClearedDisposable(this)

    override fun getLayoutRes(): Int = R.layout.activity_splash

    override fun getViewModel(): Class<SplashViewModel.ViewModel> = SplashViewModel.ViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycle.addObserver(disposables)

        disposables.add(viewModel.message
                .observeOn(schedulerProvider.ui())
                .subscribe {message ->
                    longToast(message)
                })

        disposables.add(viewModel.isSplashLoading
                .observeOn(schedulerProvider.ui())
                .subscribe {it ->
                    if (it == false)
                        startActivity<BeersViewActivity>().apply { finish() }
                })

        showSplashAnimation()
    }

    override fun onResume() {
        super.onResume()
        disposables.add(viewModel.getBeers())
    }

    private fun showSplashAnimation() {
        Glide.with(this)
                .asGif()
                .load(R.raw.beer_splash)
                .into(beer_animation)
    }


}
