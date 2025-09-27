package com.example.drodx.models;


import java.io.Serializable;

import java.io.Serializable;

public class PaymentModel implements Serializable {
    private String date;
    private long amount;  // Store amount as long (for whole numbers)

    // Default constructor
    public PaymentModel() {
    }

    // Constructor with date and amount
    public PaymentModel(String date, long amount) {
        this.date = date;
        this.amount = amount;
    }

    // Getters and Setters
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}
