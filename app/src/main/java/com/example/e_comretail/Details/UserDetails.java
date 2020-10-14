package com.example.e_comretail.Details;

public class UserDetails {
    private String username, user_mobile, user_address, user_city, user_state, user_mail, user_birthday, itemId;

    public UserDetails() {
    }

    public UserDetails(String username, String user_mobile, String user_address, String user_city, String user_state, String user_mail, String user_birthday, String itemId) {
        this.username = username;
        this.user_mobile = user_mobile;
        this.user_address = user_address;
        this.user_city = user_city;
        this.user_state = user_state;
        this.user_mail = user_mail;
        this.user_birthday = user_birthday;
        this.itemId = itemId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUser_mobile() {
        return user_mobile;
    }

    public void setUser_mobile(String user_mobile) {
        this.user_mobile = user_mobile;
    }

    public String getUser_address() {
        return user_address;
    }

    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }

    public String getUser_city() {
        return user_city;
    }

    public void setUser_city(String user_city) {
        this.user_city = user_city;
    }

    public String getUser_state() {
        return user_state;
    }

    public void setUser_state(String user_state) {
        this.user_state = user_state;
    }

    public String getUser_mail() {
        return user_mail;
    }

    public void setUser_mail(String user_mail) {
        this.user_mail = user_mail;
    }

    public String getUser_birthday() {
        return user_birthday;
    }

    public void setUser_birthday(String user_birthday) {
        this.user_birthday = user_birthday;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
}
