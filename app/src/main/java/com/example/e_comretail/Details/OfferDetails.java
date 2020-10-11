package com.example.e_comretail.Details;

public class OfferDetails {
    private String gstRate, offercategory, offerSubCategory, productID, productImageUrl, productname, productprice, offermade, offerenddate;

    public OfferDetails() {
    }

    public OfferDetails(String offercategory, String offerSubCategory, String gstRate, String productID, String productImageUrl, String productname, String productprice, String offermade, String offerenddate) {
        this.offercategory = offercategory;
        this.offerSubCategory = offerSubCategory;
        this.gstRate = gstRate;
        this.productID = productID;
        this.productImageUrl = productImageUrl;
        this.productname = productname;
        this.productprice = productprice;
        this.offermade = offermade;
        this.offerenddate = offerenddate;
    }

    public String getOffercategory() {
        return offercategory;
    }

    public void setOffercategory(String offercategory) {
        this.offercategory = offercategory;
    }

    public String getOfferSubCategory() {
        return offerSubCategory;
    }

    public void setOfferSubCategory(String offerSubCategory) {
        this.offerSubCategory = offerSubCategory;
    }

    public String getGstRate() {
        return gstRate;
    }

    public void setGstRate(String gstRate) {
        this.gstRate = gstRate;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProductprice() {
        return productprice;
    }

    public void setProductprice(String productprice) {
        this.productprice = productprice;
    }

    public String getOffermade() {
        return offermade;
    }

    public void setOffermade(String offermade) {
        this.offermade = offermade;
    }

    public String getOfferenddate() {
        return offerenddate;
    }

    public void setOfferenddate(String offerenddate) {
        this.offerenddate = offerenddate;
    }
}
