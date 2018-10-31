package home.self.beerviewer_mvvm.data.source.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import java.util.List;

import home.self.beerviewer_mvvm.data.model.BeerModel;
import home.self.beerviewer_mvvm.data.model.WishModel;
import io.reactivex.Flowable;


/**
 * Created by Tak on 2018. 1. 27..
 */

@Dao
public interface BeerDao {

    @Query("SELECT * FROM beer")
    List<BeerModel> getAllBeers();

    @Query("SELECT * FROM beer WHERE id >= :pageStart AND id <= :pageEnd")
    Flowable<List<BeerModel>> getBeers(int pageStart, int pageEnd);

    @Delete
    void deleteBeers(List<BeerModel> deletes);

    @Insert
    void insertBeers(List<BeerModel> inserts);

    @Query("SELECT * FROM beer WHERE id = :beerId")
    Flowable<BeerModel> getBeer(int beerId);


    @Query("SELECT * FROM wish WHERE id = :beerId")
    WishModel getWish(int beerId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertWish(WishModel wish);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBeer(BeerModel beer);
}
