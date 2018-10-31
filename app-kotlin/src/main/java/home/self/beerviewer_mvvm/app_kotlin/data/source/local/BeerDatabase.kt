package home.self.beerviewer_mvvm.app_kotlin.data.source.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import home.self.beerviewer_mvvm.app_kotlin.BeerViewerApplication
import home.self.beerviewer_mvvm.app_kotlin.data.model.BeerModel
import home.self.beerviewer_mvvm.app_kotlin.data.model.WishModel


/**
 * Created by Tak on 2018. 1. 27..
 */

@Database(entities = arrayOf(BeerModel::class, WishModel::class), version = 2)
abstract class BeerDatabase : RoomDatabase() {

    abstract fun beerDao(): BeerDao

    fun addBeers(beers: List<BeerModel>) {
        val previous = beerDao().allBeers
        val inserts = beers

        beerDao().deleteBeers(previous)
        beerDao().insertBeers(inserts)
    }

    fun insertOrUpdateWish(wish: WishModel) {
        beerDao().insertWish(wish)
    }

    companion object {

        private val DB_NAME = "beer_db"
        private var instance: BeerDatabase? = null

        fun getInstance(): BeerDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                        BeerViewerApplication.instance,
                        BeerDatabase::class.java,
                        DB_NAME)
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
            }

            return instance as BeerDatabase
        }
    }
}
















