package com.pekings.pos.object;


public class Ingredient {

    private final long id;
    private final String name;
    private final float price;
    private final int amount;
    private final float batchPrice;

    public Ingredient(long id, String name, float price, int amount, float batchPrice) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.batchPrice = batchPrice;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }

    public float getBatchPrice() {
        return batchPrice;
    }
}
