package com.example.drodx.models;

import java.util.Collection;

//public class UserModel {
//
//    String userId;  // Unique user ID from Firebase
//    String name;
//    String email;
//    String password;
//    String profileImg;
//
//    public UserModel() {
//        // Default constructor required for Firebase
//    }
//
//    public UserModel(String userId, String name, String email, String password) {
//        this.userId = userId;
//        this.name = name;
//        this.email = email;
//        this.password = password;
//    }
//
//    public String getUserId() {
//        return userId;
//    }
//
//    public void setUserId(String userId) {
//        this.userId = userId;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getProfileImg() {
//        return profileImg;
//    }
//
//    public void setProfileImg(String profileImg) {
//        this.profileImg = profileImg;
//    }
//
//
//}
public class UserModel {

    private String userId;  // Unique user ID from Firebase
    private String name;
    private String email;
    private String password;
    private String profileImg;
    private String phone;
    private String address;

    // Default constructor required for Firebase
    public UserModel() {
    }

    // Updated constructor including phone and address
    public UserModel(String userId, String name, String email, String password, String phone, String address, String profileImg) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.profileImg = profileImg;
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
