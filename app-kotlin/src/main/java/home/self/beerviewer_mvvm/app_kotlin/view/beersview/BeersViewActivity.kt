package home.self.beerviewer_mvvm.app_kotlin.view.beersview

import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v7.widget.LinearLayoutManager
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection
import dagger.android.support.DaggerAppCompatActivity
import home.self.beerviewer_mvvm.app_kotlin.Constant
import home.self.beerviewer_mvvm.app_kotlin.R
import home.self.beerviewer_mvvm.app_kotlin.data.model.BeerModel
import home.self.beerviewer_mvvm.app_kotlin.rx.lifecycle.AutoClearedDisposable
import home.self.beerviewer_mvvm.app_kotlin.rx.schedulers.BaseSchedulerProvider
import home.self.beerviewer_mvvm.app_kotlin.rx.schedulers.SchedulerProvider
import kotlinx.android.synthetic.main.activity_beers_view.*
import org.jetbrains.anko.startActivity
import javax.inject.Inject

class BeersViewActivity : DaggerAppCompatActivity(), SwipyRefreshLayout.OnRefreshListener, BeersAdapter.ItemClickListener {

    val disposables = AutoClearedDisposable(this)
    val viewDisposables = AutoClearedDisposable(this, false)

    @Inject lateinit var adapter: BeersAdapter
    @Inject lateinit var viewModelFactory : BeersViewModelFactory

    lateinit var viewModel : BeersViewModel

    private val handler : Handler = Handler(Looper.getMainLooper())
    private val schedulerProvider : BaseSchedulerProvider = SchedulerProvider()

    private var pageStart = 1
    private var perPage = 25

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beers_view)

        initView()

        viewModel = ViewModelProviders.of(this, viewModelFactory)[BeersViewModel::class.java]

        lifecycle.addObserver(disposables)
        lifecycle.addObserver(viewDisposables)

        getBeers(SwipyRefreshLayoutDirection.TOP)
    }

    private fun getBeers(direction: SwipyRefreshLayoutDirection) {
        viewDisposables.add(viewModel.getBeers(pageStart, perPage, direction)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe {beers ->
                    adapter.addItems(beers)
                })
    }

    private fun initView() {
        setSupportActionBar(app_bar)

        with(beer_recyler) {
            adapter = this@BeersViewActivity.adapter
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
            getBeers(direction)

            refresh_layout.isRefreshing = false
        }, Constant.REFRESH_DELAY)
    }
}
