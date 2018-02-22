package com.sqlitewithasynctask;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class DataModel {
    /*"title":"Chanel","description":"Chanel Discount",
    "img":"/containers/campaignImage/download/chanellogo.png",
    "sellersName":"T-Partner","price":1000*/

    @SerializedName("title")
    @Expose
    private String title;


    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("img")
    @Expose
    private String img;

    @SerializedName("sellersName")
    @Expose
    private String sellersName;

    @SerializedName("price")
    @Expose
    private int price;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getSellersName() {
        return sellersName;
    }

    public void setSellersName(String sellersName) {
        this.sellersName = sellersName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
