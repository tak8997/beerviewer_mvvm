package home.self.beerviewer_mvvm.app_kotlin.view.beersview

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v7.widget.LinearLayoutManager
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection
import home.self.beerviewer_mvvm.app_kotlin.BaseActivity
import home.self.beerviewer_mvvm.app_kotlin.Constant
import home.self.beerviewer_mvvm.app_kotlin.R
import home.self.beerviewer_mvvm.app_kotlin.data.model.BeerModel
import home.self.beerviewer_mvvm.app_kotlin.rx.lifecycle.AutoClearedDisposable
import home.self.beerviewer_mvvm.app_kotlin.rx.schedulers.BaseSchedulerProvider
import home.self.beerviewer_mvvm.app_kotlin.rx.schedulers.SchedulerProvider
import home.self.beerviewer_mvvm.app_kotlin.view.beerdetail.BeerDetailActivity
import kotlinx.android.synthetic.main.activity_beers_view.*
import org.jetbrains.anko.startActivity
import javax.inject.Inject

internal class BeersViewActivity : BaseActivity<BeersViewModel.ViewModel>(), SwipyRefreshLayout.OnRefreshListener, BeersAdapter.ItemClickListener {

    @Inject lateinit var beersAdapter: BeersAdapter

    val disposables = AutoClearedDisposable(this)
    val viewDisposables = AutoClearedDisposable(this, false)

    private val handler : Handler = Handler(Looper.getMainLooper())

    private var pageStart = 1
    private var perPage = 25

    override fun getLayoutRes(): Int = R.layout.activity_beers_view

    override fun getViewModel(): Class<BeersViewModel.ViewModel> = BeersViewModel.ViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()


        lifecycle.addObserver(disposables)
        lifecycle.addObserver(viewDisposables)

        viewDisposables.add(viewModel.beers
                .observeOn(schedulerProvider.ui())
                .subscribe { beers ->
                    beersAdapter.addItems(beers)
                })

        viewDisposables.add(viewModel.getIndex()
                .subscribe { index ->
                    pageStart = index
                })

        disposables.add(viewModel.getBeers(pageStart++, perPage, SwipyRefreshLayoutDirection.TOP))
    }

    private fun initView() {
        setSupportActionBar(app_bar)

        with(beer_recyler) {
            adapter = this@BeersViewActivity.beersAdapter
            layoutManager = LinearLayoutManager(this@BeersViewActivity)
        }

        refresh_layout.setOnRefreshListener(this)
        refresh_layout.setColorSchemeColors(Color.YELLOW, Color.RED, Color.GREEN)
    }

    override fun onItemClick(beer: BeerModel) {
        startActivity<BeerDetailActivity>(Constant.KEY_BEAR_ID to beer.id)
    }

    override fun onRefresh(direction: SwipyRefreshLayoutDirection) {
        handler.postDelayed({
            viewModel.getBeers(pageStart++, perPage, direction)

            refresh_layout.isRefreshing = false
        }, Constant.REFRESH_DELAY)
    }
}
