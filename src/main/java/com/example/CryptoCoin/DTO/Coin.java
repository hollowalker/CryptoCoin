package com.example.CryptoCoin.DTO;

public class Coin {

    String status;
    String uuid;
    String name;
    String curCurrency;
    double price;
    String imgUrl;
    double priceChangedPercentage24h;

    public Coin(String uuid, String name, String curCurrency, double price, String imgUrl, double priceChangedPercentage24h) {
        status = "200";
        this.uuid = uuid;
        this.name = name;
        this.curCurrency = curCurrency;
        this.price = price;
        this.imgUrl = imgUrl;
        this.priceChangedPercentage24h = priceChangedPercentage24h;
    }

    public Coin(String status) {
        this.status = status;
        this.uuid = "";
        this.name = "";
        this.curCurrency = "";
        this.price = 0;
        this.imgUrl = "";
        this.priceChangedPercentage24h = 0;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurCurrency() {
        return curCurrency;
    }

    public void setCurCurrency(String curCurrency) {
        this.curCurrency = curCurrency;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public double getPriceChangedPercentage24h() {
        return priceChangedPercentage24h;
    }

    public void setPriceChangedPercentage24h(double priceChangedPercentage24h) {
        this.priceChangedPercentage24h = priceChangedPercentage24h;
    }
}
