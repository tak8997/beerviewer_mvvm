package home.self.beerviewer_mvvm.app_kotlin.view.splash

import android.os.Bundle
import com.bumptech.glide.Glide
import home.self.beerviewer_mvvm.app_kotlin.base.BaseActivity
import home.self.beerviewer_mvvm.app_kotlin.base.Constants
import home.self.beerviewer_mvvm.app_kotlin.R
import home.self.beerviewer_mvvm.app_kotlin.view.beersview.BeersViewActivity
import kotlinx.android.synthetic.main.activity_splash.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

internal class SplashActivity : BaseActivity<SplashViewModel.ViewModel>() {

    override fun getLayoutRes(): Int = R.layout.activity_splash

    override fun getViewModel(): Class<SplashViewModel.ViewModel> = SplashViewModel.ViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        subscribe()

        showSplashAnimation()
    }

    private fun subscribe() {
        viewModel.outputs.errorMessage().observe { message ->
            toast(message)
        }

        viewModel.outputs.isSplashLoadingEnd().observe { pair ->
            startActivity<BeersViewActivity>(
                    Constants.EXTRA_DEFAULT_PAGE to pair
            ).apply { finish() }
        }
    }

    private fun showSplashAnimation() {
        Glide.with(this)
                .asGif()
                .load(R.raw.beer_splash)
                .into(beer_animation)
    }

}
