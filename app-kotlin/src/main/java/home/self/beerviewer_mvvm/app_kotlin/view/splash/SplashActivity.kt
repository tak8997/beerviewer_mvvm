package home.self.beerviewer_mvvm.app_kotlin.view.splash

import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import home.self.beerviewer_mvvm.app_kotlin.BaseActivity
import home.self.beerviewer_mvvm.app_kotlin.R
import home.self.beerviewer_mvvm.app_kotlin.extensions.observe
import home.self.beerviewer_mvvm.app_kotlin.rx.lifecycle.AutoClearedDisposable
import home.self.beerviewer_mvvm.app_kotlin.view.beersview.BeersViewActivity
import kotlinx.android.synthetic.main.activity_splash.*
import org.jetbrains.anko.longToast
import org.jetbrains.anko.startActivity

internal class SplashActivity : BaseActivity<SplashViewModel.ViewModel>() {

    override fun getLayoutRes(): Int = R.layout.activity_splash

    override fun getViewModel(): Class<SplashViewModel.ViewModel> = SplashViewModel.ViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        subscribeLooknFeel()

        showSplashAnimation()
    }

    private fun subscribeLooknFeel() {
        observe(viewModel.outputs.errorMessage(), ::handleMessage)
        observe(viewModel.outputs.isSplashLoadingEnd(), ::handleSplashLoading)
    }

    private fun handleSplashLoading(isLoading: Boolean) {
        startActivity<BeersViewActivity>()
    }

    private fun handleMessage(message: String) {
        longToast(message)
    }

    private fun showSplashAnimation() {
        Glide.with(this)
                .asGif()
                .load(R.raw.beer_splash)
                .into(beer_animation)
    }

}
