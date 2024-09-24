package com.pekings.pos.object;

import java.sql.Date;

public class Ingredient {

    private final long id;
    private final String name;
    private final int price;
    private final int amount;
    private final int batchPrice;
    private final Date timeReceived;
    private final Date expirationDate;

    public Ingredient(long id, String name, int price, int amount, int batchPrice, Date timeReceived, Date expirationDate) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.batchPrice = batchPrice;
        this.timeReceived = timeReceived;
        this.expirationDate = expirationDate;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }

    public int getBatchPrice() {
        return batchPrice;
    }

    public Date getTimeReceived() {
        return timeReceived;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }
}
