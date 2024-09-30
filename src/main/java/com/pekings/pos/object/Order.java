package com.pekings.pos.object;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Order {

    private final int id;
    private final int customerID;
    private final double price;
    private final String paymentMethod;
    private final Date purchaseTime;
    private final List<MenuItem> itemsSold;
    private final long employeeID;

    public Order(int id, int customerID, double price, String paymentMethod, Date purchaseTime, int employeeID) {
        this.id = id;
        this.customerID = customerID;
        this.price = price;
        this.paymentMethod = paymentMethod;
        this.purchaseTime = purchaseTime;
        this.employeeID = employeeID;
        this.itemsSold = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public int getCustomerID() {
        return customerID;
    }

    public double getPrice() {
        return price;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public Date getPurchaseTime() {
        return purchaseTime;
    }

    public List<MenuItem> getItemsSold() {
        return new ArrayList<>(itemsSold);
    }

    public void addItem(MenuItem item) {
        itemsSold.add(item);
    }

    public long getEmployeeID() {
        return employeeID;
    }
}
