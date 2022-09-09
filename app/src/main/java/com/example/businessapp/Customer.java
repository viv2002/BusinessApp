package com.example.businessapp;

public class Customer {
    private String customerName;
    private String debtAmount;
    private String phoneNo;

    public Customer() {}

    public Customer(String customerName, String phoneNo, String debtAmount) {
        this.customerName = customerName;
        this.debtAmount = debtAmount;
        this.phoneNo = phoneNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getDebtAmount() {
        return debtAmount;
    }

    public void setDebtAmount(String amount) {
        debtAmount = amount;
    }
}
