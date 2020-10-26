package com.example.e_comretail.Details;

public class UserDetails {
    private String userImage, userMail, username, userUID;

    public UserDetails() {
    }

    public UserDetails(String userImage, String userMail, String username, String userUID) {
        this.userImage = userImage;
        this.userMail = userMail;
        this.username = username;
        this.userUID = userUID;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }
}
