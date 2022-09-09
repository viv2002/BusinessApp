package com.example.businessapp;

public class Stock {
    private String stockName;
    private String stockQuantity;
    private String stockPrice;

    public Stock() {}

    public Stock(String stockName, String stockQuantity, String stockPrice) {
        this.stockName = stockName;
        this.stockQuantity = stockQuantity;
        this.stockPrice = stockPrice;
    }

    public String getStockName() {
        return stockName;
    }

    public String getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(String quantity) {
        stockQuantity = quantity;
    }

    public String getStockPrice() {
        return stockPrice;
    }

    public void setStockPrice(String price) {
        stockPrice = price;
    }
}
