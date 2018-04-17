package home.self.beerviewer_mvvm.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Tak on 2018. 1. 28..
 */

@Entity(tableName = "wish",
        foreignKeys = @ForeignKey(
                entity = BeerModel.class,
                parentColumns = "id",
                childColumns = "beer_id"
        )
)
public class WishModel {
    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "beer_id")
    private long beerId;

    @ColumnInfo(name = "wish")
    private boolean wish;

    public WishModel(long beerId, boolean wish) {
        this.beerId = beerId;
        this.wish = wish;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getBeerId() {
        return beerId;
    }

    public void setBeerId(long beerId) {
        this.beerId = beerId;
    }

    public boolean isWish() {
        return wish;
    }

    public void setWish(boolean wish) {
        this.wish = wish;
    }
}

















