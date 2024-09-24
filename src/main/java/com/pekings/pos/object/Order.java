package com.pekings.pos.object;

import java.sql.Date;
import java.util.List;

public class Order {

    private final long id;
    private final long customerID;
    private final int price;
    private final String paymentMethod;
    private final Date purchaseTime;
    private final List<MenuItem> itemsSold;

    public Order(long id, long customerID, int price, String paymentMethod, Date purchaseTime, List<MenuItem> itemsSold) {
        this.id = id;
        this.customerID = customerID;
        this.price = price;
        this.paymentMethod = paymentMethod;
        this.purchaseTime = purchaseTime;
        this.itemsSold = itemsSold;
    }

    public long getId() {
        return id;
    }

    public long getCustomerID() {
        return customerID;
    }

    public int getPrice() {
        return price;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public Date getPurchaseTime() {
        return purchaseTime;
    }

    public List<MenuItem> getItemsSold() {
        return itemsSold;
    }
}
