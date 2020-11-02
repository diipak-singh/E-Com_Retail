package com.example.e_comretail.Details;

public class RatingDetails {
    private String productCode, userId, userName, reviewDate, rating, reviewText, itemId;

    public RatingDetails() {
    }

    public RatingDetails(String productCode, String userId, String userName, String reviewDate, String rating, String reviewText, String itemId) {
        this.productCode = productCode;
        this.userId = userId;
        this.userName = userName;
        this.reviewDate = reviewDate;
        this.rating = rating;
        this.reviewText = reviewText;
        this.itemId = itemId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(String reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
}
