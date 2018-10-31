package home.self.beerviewer_mvvm.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Tak on 2018. 1. 27..
 */

public class Hops {
    @SerializedName("name")
    @ColumnInfo(name = "name")
    String name;

    @SerializedName("amount")
    @Embedded
    Amount amount;

    @SerializedName("add")
    @ColumnInfo(name = "add")
    String add;

    @SerializedName("attribute")
    @ColumnInfo(name = "attribute")
    String attribute;

    public Hops(String name, Amount amount, String add, String attribute) {
        this.name = name;
        this.amount = amount;
        this.add = add;
        this.attribute = attribute;
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

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }
}
