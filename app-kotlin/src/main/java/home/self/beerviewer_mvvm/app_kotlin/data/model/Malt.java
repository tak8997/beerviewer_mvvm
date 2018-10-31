package home.self.beerviewer_mvvm.app_kotlin.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Tak on 2018. 1. 27..
 */

public class Malt {
    @SerializedName("name")
    @ColumnInfo(name = "name")
    String name;

    @SerializedName("amount")
    @Embedded
    Amount amount;

    public Malt(String name, Amount amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Amount getAmount() {
        return amount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }
}
