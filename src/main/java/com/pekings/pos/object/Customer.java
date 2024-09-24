package com.pekings.pos.object;

import java.sql.Date;

public class Customer {

    private final long id;
    private final String phoneNumber;
    private final String email;
    private final Date lastPurchase;

    public Customer(long id, String phoneNumber, String email, Date lastPurchase) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.lastPurchase = lastPurchase;
    }

    public long getId() {
        return id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public Date getLastPurchase() {
        return lastPurchase;
    }
}
