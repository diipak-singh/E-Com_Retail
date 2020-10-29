package com.example.e_comretail.Details;

public class OrderDetails {
    String itemName, itemCode, measurement, image, orderStatus, orderBill, estimatedTime, totalAmount, HsnCode, GstRate, itemQuantity, orderTime, orderDate, userName, userMail, userPhone, userAddress, userLandMark, userCity, userState, userZip, userID, paidVia, orderID;

    public OrderDetails() {
    }

    public OrderDetails(String itemName, String itemCode, String measurement, String image, String orderStatus, String orderBill, String estimatedTime, String totalAmount, String hsnCode, String gstRate, String itemQuantity, String orderTime, String orderDate, String userName, String userMail, String userPhone, String userAddress, String userLandMark, String userCity, String userState, String userZip, String userID, String paidVia, String orderID) {
        this.itemName = itemName;
        this.itemCode = itemCode;
        this.measurement = measurement;
        this.image = image;
        this.orderStatus = orderStatus;
        this.orderBill = orderBill;
        this.estimatedTime = estimatedTime;
        this.totalAmount = totalAmount;
        HsnCode = hsnCode;
        GstRate = gstRate;
        this.itemQuantity = itemQuantity;
        this.orderTime = orderTime;
        this.orderDate = orderDate;
        this.userName = userName;
        this.userMail = userMail;
        this.userPhone = userPhone;
        this.userAddress = userAddress;
        this.userLandMark = userLandMark;
        this.userCity = userCity;
        this.userState = userState;
        this.userZip = userZip;
        this.userID = userID;
        this.paidVia = paidVia;
        this.orderID = orderID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderBill() {
        return orderBill;
    }

    public void setOrderBill(String orderBill) {
        this.orderBill = orderBill;
    }

    public String getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(String estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getHsnCode() {
        return HsnCode;
    }

    public void setHsnCode(String hsnCode) {
        HsnCode = hsnCode;
    }

    public String getGstRate() {
        return GstRate;
    }

    public void setGstRate(String gstRate) {
        GstRate = gstRate;
    }

    public String getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserLandMark() {
        return userLandMark;
    }

    public void setUserLandMark(String userLandMark) {
        this.userLandMark = userLandMark;
    }

    public String getUserCity() {
        return userCity;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    public String getUserState() {
        return userState;
    }

    public void setUserState(String userState) {
        this.userState = userState;
    }

    public String getUserZip() {
        return userZip;
    }

    public void setUserZip(String userZip) {
        this.userZip = userZip;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPaidVia() {
        return paidVia;
    }

    public void setPaidVia(String paidVia) {
        this.paidVia = paidVia;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }
}
