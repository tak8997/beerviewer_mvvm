package home.self.beerviewer_mvvm.app_kotlin.view.beersview

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection
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
        var addLocation = 0

        if (beers[0].direction == SwipyRefreshLayoutDirection.TOP)
            addLocation = 0
        else if (beers[0].direction == SwipyRefreshLayoutDirection.BOTTOM)
            addLocation = items.size

        this.items.addAll(addLocation, beers)
        this.notifyDataSetChanged()
    }

    class BeersViewHolder(parent : ViewGroup) : RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context)
                    .inflate(R.layout.beer_item, parent, false))
}
