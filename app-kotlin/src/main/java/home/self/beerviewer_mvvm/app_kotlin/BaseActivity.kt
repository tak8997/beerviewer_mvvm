package home.self.beerviewer_mvvm.app_kotlin

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import dagger.android.AndroidInjection
import javax.inject.Inject

internal abstract class BaseActivity<VM> : AppCompatActivity() where VM : BaseViewModel {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    @LayoutRes
    protected abstract fun getLayoutRes(): Int

    protected abstract fun getViewModel(): Class<VM>

    protected lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(getLayoutRes())

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(getViewModel())
        viewModel.intent(intent)
    }
}
