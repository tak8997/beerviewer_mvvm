package home.self.beerviewer_mvvm.app_kotlin.view.beersview

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v7.widget.LinearLayoutManager
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection
import home.self.beerviewer_mvvm.app_kotlin.BaseActivity
import home.self.beerviewer_mvvm.app_kotlin.Constants
import home.self.beerviewer_mvvm.app_kotlin.R
import home.self.beerviewer_mvvm.app_kotlin.data.model.BeerModel
import home.self.beerviewer_mvvm.app_kotlin.extensions.observe
import home.self.beerviewer_mvvm.app_kotlin.extensions.showDialogFragment
import home.self.beerviewer_mvvm.app_kotlin.rx.lifecycle.AutoClearedDisposable
import home.self.beerviewer_mvvm.app_kotlin.view.beerdetail.BeerDetailActivity
import kotlinx.android.synthetic.main.activity_beers_view.*
import org.jetbrains.anko.startActivity
import javax.inject.Inject

internal class BeersViewActivity : BaseActivity<BeersViewModel.ViewModel>(), SwipyRefreshLayout.OnRefreshListener, BeersAdapter.ItemClickListener {

    @Inject lateinit var beersAdapter: BeersAdapter

    val disposables = AutoClearedDisposable(this)
    val viewDisposables = AutoClearedDisposable(this, false)

    private val handler : Handler = Handler(Looper.getMainLooper())

    private var pageStart = 2
    private var perPage = 25

    override fun getLayoutRes(): Int = R.layout.activity_beers_view

    override fun getViewModel(): Class<BeersViewModel.ViewModel> = BeersViewModel.ViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        subscribeLooknFeel()

        initializeViews()


//        lifecycle.addObserver(disposables)
//        lifecycle.addObserver(viewDisposables)
//
//        viewDisposables.add(viewModel.beers
//                .observeOn(schedulerProvider.ui())
//                .subscribe { beers ->
//                    beersAdapter.addItems(beers)
//                })
//
//        viewDisposables.add(viewModel.getIndex()
//                .subscribe { index ->
//                    pageStart = index
//                })
//
//        disposables.add(viewModel.getBeers(pageStart++, perPage, SwipyRefreshLayoutDirection.TOP))
    }

    private fun subscribeLooknFeel() {
        observe(viewModel.outpus.isLoading(), ::handleIsLoading)
    }

    private fun handleIsLoading(isLoading: Boolean) {
        if(isLoading) {
            showLoadingDialog()
        } else {
            hideLoadingDialog()
        }
    }

    private fun initializeViews() {
        setSupportActionBar(app_bar)

        with(beer_recyler) {
            adapter = this@BeersViewActivity.beersAdapter
            layoutManager = LinearLayoutManager(this@BeersViewActivity)
        }

        refresh_layout.setOnRefreshListener(this)
        refresh_layout.setColorSchemeColors(Color.YELLOW, Color.RED, Color.GREEN)
    }

    override fun onItemClick(beer: BeerModel) {
        startActivity<BeerDetailActivity>(Constants.KEY_BEAR_ID to beer.id)
    }

    override fun onRefresh(direction: SwipyRefreshLayoutDirection) {
        handler.postDelayed({
            viewModel.outpus.fetchBeers(pageStart++, perPage, direction)

            refresh_layout.isRefreshing = false
        }, Constants.REFRESH_DELAY)
    }
}
