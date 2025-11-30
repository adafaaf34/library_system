package com.example.library.model;

public class User {
    private Long userId;
    private String phoneNumber;
    private String passwordHash;
    private String salt;
    private String userName;
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public String getSalt() { return salt; }
    public void setSalt(String salt) { this.salt = salt; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
}
