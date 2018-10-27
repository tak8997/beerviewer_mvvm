package home.self.beerviewer_mvvm.app_kotlin.view.beerdetail

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import home.self.beerviewer_mvvm.app_kotlin.BaseActivity
import home.self.beerviewer_mvvm.app_kotlin.Parameter
import home.self.beerviewer_mvvm.app_kotlin.R
import home.self.beerviewer_mvvm.app_kotlin.data.model.BeerModel
import home.self.beerviewer_mvvm.app_kotlin.extensions.observe
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_beer_detail.*
import org.jetbrains.anko.toast


internal class BeerDetailActivity : BaseActivity<BeerDetailViewModel.ViewModel>() {

    override fun getLayoutRes(): Int = R.layout.activity_beer_detail

    override fun getViewModel(): Class<BeerDetailViewModel.ViewModel> = BeerDetailViewModel.ViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        subscribe()

        setSupportActionBar(app_bar)
    }

    private fun subscribe() {
        handleFetchBeer()
        handleFetchBeerInfo()

        observe(viewModel.outputs.message(), ::handleMessage)
        observe(viewModel.outputs.isLoading(), ::handleIsLoading)
    }

    private fun handleFetchBeer() {
        viewModel.outputs.fetchBeer().subscribeBy { beer ->
            beer?.let { it ->
                Glide.with(this)
                        .load(it.imageUrl)
                        .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                        .into(beer_img)
                beer_title.text = it.name
                beer_tagline.text = it.tagline
                beer_description.text = it.description
                beer_brewers_tips.text = it.brewersTips
                beer_contributed_by.text = it.contributedBy
                beer_first_brewed.text = it.firstBrewed
                app_bar_title.text = it.name
            }
        }
    }

    private fun handleFetchBeerInfo() {
        viewModel.outputs.fetchBeerInfo().subscribeBy { beerInfo ->
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"

                putExtra(Intent.EXTRA_TEXT, beerInfo)
            }

            startActivity(Intent.createChooser(intent, resources.getText(R.string.send_to)))
        }
    }

    private fun handleMessage(message: String) {
        toast(message)
    }

    private fun handleIsLoading(isLoading: Boolean) {
        if(isLoading) {
            showLoadingDialog()
        } else {
            hideLoadingDialog()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.share -> {
                viewModel.inputs.clickBeerInfo(Parameter.CLICK)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
