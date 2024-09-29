package com.pekings.pos.storage;

import com.pekings.pos.object.*;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Repository {

    void addMenuItem(MenuItem menuItem);

    MenuItem getMenuItem(int itemID);

    List<MenuItem> getOrderItems(int orderID);

    double getDailyIncome(Date date);

    double getWeeklyIncome(Date startOfWeek);

    double getMonthlyIncome(Date startOfMonth);

    double getIncome(Date from, Date to);

    List<Employee> getActiveEmployees();

    List<Employee> getEmployees();

    Set<MenuItem> getMenuItems();

    Ingredient getIngredient(int id);

    Map<MenuItem, Double> getTopMenuItemsRevenue(int topWhat);

    Map<MenuItem, Integer> getTopMenuItemsOrders(int topWhat);

    Map<MenuItem, Double> getTopMenuItemsRevenue(Date from, Date to);

    Map<MenuItem, Integer> getTopMenuItemsOrders(Date from, Date to);

    List<Ingredient> getIngredients(int menuItemID);

    Map<String, Integer> getTopPaymentMethods();

    Map<Customer, Double> getTopCustomersRevenue(int topWhat);

    Map<Customer, Integer> getTopCustomersOrders(int topWhat);

    List<Order> getOrders(int customerID);

    Customer getCustomer(int customerID);
}
