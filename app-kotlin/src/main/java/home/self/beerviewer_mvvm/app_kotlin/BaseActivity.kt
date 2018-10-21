package home.self.beerviewer_mvvm.app_kotlin

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import dagger.android.AndroidInjection
import home.self.beerviewer_mvvm.app_kotlin.extensions.showDialogFragment
import home.self.beerviewer_mvvm.app_kotlin.rx.lifecycle.AutoClearedDisposable
import home.self.beerviewer_mvvm.app_kotlin.rx.schedulers.BaseSchedulerProvider
import javax.inject.Inject

internal abstract class BaseActivity<VM> : AppCompatActivity() where VM : BaseViewModel {

    companion object {
        private const val LOADING_DIALOG = "loading_dialog"
    }

    @Inject lateinit var schedulerProvider: BaseSchedulerProvider

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    @LayoutRes
    protected abstract fun getLayoutRes(): Int

    protected abstract fun getViewModel(): Class<VM>

    protected lateinit var viewModel: VM

    private var loadingFragment: DialogFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(getLayoutRes())

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(getViewModel())
        viewModel.intent(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        viewModel.onActivityResult(requestCode, resultCode, data)
    }

    protected fun showLoadingDialog() {
        if(loadingFragment == null) {
            loadingFragment = LoadingDialogFragment()

            showDialogFragment(loadingFragment!!, LOADING_DIALOG)
        }
    }

    protected fun hideLoadingDialog() {
        loadingFragment?.let {
            it.dismissAllowingStateLoss()

            null
        }
    }

}
