package com.example.e_comretail.Details;

import java.io.Serializable;

public class CartDetails implements Serializable {
    private String itemCode, stock, itemName, itemPrice, measurement, itemQuantity, discount, AmountPayable, itemImage, itemId;


    public CartDetails(String itemCode, String stock, String itemName, String itemPrice, String measurement, String itemQuantity, String discount, String amountPayable, String itemImage, String itemId) {
        this.itemCode = itemCode;
        this.stock = stock;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.measurement = measurement;
        this.itemQuantity = itemQuantity;
        this.discount = discount;
        AmountPayable = amountPayable;
        this.itemImage = itemImage;
        this.itemId = itemId;
    }

    public CartDetails() {
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public String getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getAmountPayable() {
        return AmountPayable;
    }

    public void setAmountPayable(String amountPayable) {
        AmountPayable = amountPayable;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
}
