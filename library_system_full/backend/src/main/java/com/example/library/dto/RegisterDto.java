package com.example.library.dto;

import jakarta.validation.constraints.NotBlank;

public class RegisterDto {
    @NotBlank
    private String phone;
    @NotBlank
    private String password;
    private String userName;
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
}
