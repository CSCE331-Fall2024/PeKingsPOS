package com.pekings.pos.util;

import java.sql.Timestamp;

public class SaleHistoryItem {

    private Timestamp time;
    private int totalOrders;
    private double totalRevenue;

    public SaleHistoryItem(Timestamp time, int totalOrders, double totalRevenue) {
        this.time = time;
        this.totalOrders = totalOrders;
        this.totalRevenue = totalRevenue;
    }

    public Timestamp getTime() {
        return time;
    }

    public int getTotalOrders() {
        return totalOrders;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }
}
