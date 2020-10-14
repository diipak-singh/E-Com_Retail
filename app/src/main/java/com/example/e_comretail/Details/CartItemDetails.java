package com.example.e_comretail.Details;

public class CartItemDetails {
    private String itemName, itemPrice, itemImage, itemQuantity, itemTotalPrice, itemId;

    public CartItemDetails() {
    }

    public CartItemDetails(String itemName, String itemPrice, String itemImage, String itemQuantity, String itemTotalPrice, String itemId) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemImage = itemImage;
        this.itemQuantity = itemQuantity;
        this.itemTotalPrice = itemTotalPrice;
        this.itemId = itemId;
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

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public String getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getItemTotalPrice() {
        return itemTotalPrice;
    }

    public void setItemTotalPrice(String itemTotalPrice) {
        this.itemTotalPrice = itemTotalPrice;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
}
