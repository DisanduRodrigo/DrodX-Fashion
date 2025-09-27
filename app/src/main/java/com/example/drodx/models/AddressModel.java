package com.example.drodx.models;

public class AddressModel {

    String userAddressName;
    String userAddressLane;
    String userAddressCity;
    String userAddressPostalCode;
    String userAddressNumber;
    boolean isSelected;

    public AddressModel() {
    }

    public String getUserAddressName() {
        return userAddressName;
    }

    public void setUserAddressName(String userAddressName) {
        this.userAddressName = userAddressName;
    }

    public String getUserAddressLane() {
        return userAddressLane;
    }

    public void setUserAddressLane(String userAddressLane) {
        this.userAddressLane = userAddressLane;
    }

    public String getUserAddressCity() {
        return userAddressCity;
    }

    public void setUserAddressCity(String userAddressCity) {
        this.userAddressCity = userAddressCity;
    }

    public String getUserAddressPostalCode() {
        return userAddressPostalCode;
    }

    public void setUserAddressPostalCode(String userAddressPostalCode) {
        this.userAddressPostalCode = userAddressPostalCode;
    }

    public String getUserAddressNumber() {
        return userAddressNumber;
    }

    public void setUserAddressNumber(String userAddressNumber) {
        this.userAddressNumber = userAddressNumber;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
