package home.self.beerviewer_mvvm.app_kotlin.view.beerdetail

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import dagger.android.support.DaggerAppCompatActivity
import home.self.beerviewer_mvvm.app_kotlin.R
import home.self.beerviewer_mvvm.app_kotlin.rx.schedulers.BaseSchedulerProvider
import kotlinx.android.synthetic.main.activity_beer_detail.*
import org.jetbrains.anko.longToast
import javax.inject.Inject


class BeerDetailActivity : DaggerAppCompatActivity() {

    @Inject lateinit var viewModelFactory: BeerDetailViewModelFactory
    @Inject lateinit var schedulerProvider: BaseSchedulerProvider

    private lateinit var viewModel: BeerDetailViewModel

    private var beerInfo: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beer_detail)

        viewModel = ViewModelProviders.of(this, viewModelFactory)[BeerDetailViewModel::class.java]

        viewModel.beer
                .observeOn(schedulerProvider.ui())
                .subscribe { beer ->
                    beerInfo = (beer.name + "\n" + beer.tagline + "\n" + beer.description + "\n"
                            + beer.brewersTips + "\n" + beer.contributedBy + "\n" + beer.firstBrewed)

                    Glide.with(this)
                            .load(beer.imageUrl)
                            .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                            .into(beer_img)
                    beer_title.text = beer.name
                    beer_tagline.text = beer.tagline
                    beer_description.text = beer.description
                    beer_brewers_tips.text = beer.brewersTips
                    beer_contributed_by.text = beer.contributedBy
                    beer_first_brewed.text = beer.firstBrewed
                    app_bar_title.text = beer.name
                }

        viewModel.message
                .observeOn(schedulerProvider.ui())
                .subscribe { msg -> longToast(msg) }

        viewModel.isLoading
                .observeOn(schedulerProvider.ui())
                .subscribe { isLoading ->
                    if (isLoading) {

                    } else {

                    }
                }

        viewModel.beerInfo
                .observeOn(schedulerProvider.ui())
                .subscribe { beerInfo ->
                    val intent = Intent()
                    intent.action = Intent.ACTION_SEND
                    intent.putExtra(Intent.EXTRA_TEXT, beerInfo)
                    intent.type = "text/plain"
                    startActivity(Intent.createChooser(intent, resources.getText(R.string.send_to)))
                }

        viewModel.getBeer()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.share -> {
                beerInfo?.let { viewModel.beerInfo.onNext(it) }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
