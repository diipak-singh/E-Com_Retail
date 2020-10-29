package com.example.e_comretail.Details;

public class ChatDetails {
    private String userName, userMail, userUid, userMessage, adminMessage, messageDate, messageTime;

    public ChatDetails() {
    }

    public ChatDetails(String userName, String userMail, String userUid, String userMessage, String adminMessage, String messageDate, String messageTime) {
        this.userName = userName;
        this.userMail = userMail;
        this.userUid = userUid;
        this.userMessage = userMessage;
        this.adminMessage = adminMessage;
        this.messageDate = messageDate;
        this.messageTime = messageTime;
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

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public String getAdminMessage() {
        return adminMessage;
    }

    public void setAdminMessage(String adminMessage) {
        this.adminMessage = adminMessage;
    }

    public String getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(String messageDate) {
        this.messageDate = messageDate;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }
}
