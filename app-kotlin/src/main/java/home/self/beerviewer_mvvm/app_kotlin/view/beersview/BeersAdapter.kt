package home.self.beerviewer_mvvm.app_kotlin.view.beersview

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import home.self.beerviewer_mvvm.app_kotlin.R
import home.self.beerviewer_mvvm.app_kotlin.data.model.BeerModel
import java.util.*

/**
 * Created by Tak on 2018. 1. 27..
 */

class BeersAdapter : RecyclerView.Adapter<BeersAdapter.BeersViewHolder>() {

    private val items = ArrayList<BeerModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BeersViewHolder(parent)

    override fun onBindViewHolder(holder: BeersViewHolder, position: Int) {
        items[position].let { item -> }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addItems(beers: List<BeerModel>) {
        this.items.addAll(0, beers)
        this.notifyDataSetChanged()
    }

    fun addItemsFromBottom(beers: List<BeerModel>) {
        this.items.addAll(items.size, beers)
        this.notifyDataSetChanged()
    }

    class BeersViewHolder(parent : ViewGroup) : RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context)
                    .inflate(R.layout.beer_item, parent, false))
}
