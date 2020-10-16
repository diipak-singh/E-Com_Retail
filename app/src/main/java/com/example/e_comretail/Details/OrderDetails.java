package com.example.e_comretail.Details;

public class OrderDetails {
    String itemName, totalAmount, itemQuantity, orderTime, orderDate, userName, userPhone, userAddress,  userID, paidVia,orderID;

    public OrderDetails() {
    }

    public OrderDetails(String itemName, String totalAmount, String itemQuantity, String orderTime, String orderDate, String userName, String userPhone, String userAddress, String userID, String paidVia, String orderID) {
        this.itemName = itemName;
        this.totalAmount = totalAmount;
        this.itemQuantity = itemQuantity;
        this.orderTime = orderTime;
        this.orderDate = orderDate;
        this.userName = userName;
        this.userPhone = userPhone;
        this.userAddress = userAddress;
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

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
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
