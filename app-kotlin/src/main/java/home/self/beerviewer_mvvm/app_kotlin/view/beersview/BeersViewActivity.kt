package home.self.beerviewer_mvvm.app_kotlin.view.beersview

import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection
import dagger.android.support.DaggerAppCompatActivity
import home.self.beerviewer_mvvm.app_kotlin.Constant

import home.self.beerviewer_mvvm.app_kotlin.R
import home.self.beerviewer_mvvm.app_kotlin.extensions.AutoActivatedDisposable
import home.self.beerviewer_mvvm.app_kotlin.extensions.AutoClearedDisposable
import kotlinx.android.synthetic.main.activity_beers_view.*
import javax.inject.Inject

class BeersViewActivity : DaggerAppCompatActivity(), SwipyRefreshLayout.OnRefreshListener {

    val disposables = AutoClearedDisposable(this)
    val viewDisposables = AutoClearedDisposable(this, false)

    @Inject lateinit var adapter: BeersAdapter
    @Inject lateinit var viewModelFactory : BeersViewModelFactory

    lateinit var viewModel : BeersViewModel

    private val handler : Handler = Handler(Looper.getMainLooper())

    private var pageStart = 1
    private var perPage = 25

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beers_view)

        initView()

        viewModel = ViewModelProviders.of(this, viewModelFactory)[BeersViewModel::class.java]

        lifecycle.addObserver(disposables)
        lifecycle.addObserver(viewDisposables)

       viewDisposables.add(viewModel.getBeers(pageStart, perPage, SwipyRefreshLayoutDirection.TOP))
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

    override fun onRefresh(direction: SwipyRefreshLayoutDirection?) {
        handler.postDelayed(Runnable {
            viewModel.getBeers(pageStart, perPage, SwipyRefreshLayoutDirection.TOP)
            refresh_layout.isRefreshing = false
        }, Constant.REFRESH_DELAY)
    }
}
