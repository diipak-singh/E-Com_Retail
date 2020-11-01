package com.example.e_comretail.Details;

public class WishlistDetails {
    private String ItemName, ItemPrice, Gst, Desc, IsCertified, Measurement, Discount, stock, ItemCode, HsnCode, image1, image2, image3, image4, image5, image6, itemId;

    public WishlistDetails() {
    }

    public WishlistDetails(String itemName, String itemPrice, String gst, String desc, String isCertified, String measurement, String discount, String stock, String itemCode, String hsnCode, String image1, String image2, String image3, String image4, String image5, String image6, String itemId) {
        ItemName = itemName;
        ItemPrice = itemPrice;
        Gst = gst;
        Desc = desc;
        IsCertified = isCertified;
        Measurement = measurement;
        Discount = discount;
        this.stock = stock;
        ItemCode = itemCode;
        HsnCode = hsnCode;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.image4 = image4;
        this.image5 = image5;
        this.image6 = image6;
        this.itemId = itemId;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getItemPrice() {
        return ItemPrice;
    }

    public void setItemPrice(String itemPrice) {
        ItemPrice = itemPrice;
    }

    public String getGst() {
        return Gst;
    }

    public void setGst(String gst) {
        Gst = gst;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public String getIsCertified() {
        return IsCertified;
    }

    public void setIsCertified(String isCertified) {
        IsCertified = isCertified;
    }

    public String getMeasurement() {
        return Measurement;
    }

    public void setMeasurement(String measurement) {
        Measurement = measurement;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getItemCode() {
        return ItemCode;
    }

    public void setItemCode(String itemCode) {
        ItemCode = itemCode;
    }

    public String getHsnCode() {
        return HsnCode;
    }

    public void setHsnCode(String hsnCode) {
        HsnCode = hsnCode;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public String getImage4() {
        return image4;
    }

    public void setImage4(String image4) {
        this.image4 = image4;
    }

    public String getImage5() {
        return image5;
    }

    public void setImage5(String image5) {
        this.image5 = image5;
    }

    public String getImage6() {
        return image6;
    }

    public void setImage6(String image6) {
        this.image6 = image6;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
}
