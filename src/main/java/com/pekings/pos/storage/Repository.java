package com.pekings.pos.storage;

import com.pekings.pos.object.*;
import com.pekings.pos.util.SaleHistoryItem;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Repository {

    void addMenuItem(MenuItem menuItem);

    void deleteMenuItem(int menuItemID);

    MenuItem getMenuItem(int itemID);

    void updateMenuItem(MenuItem menuItem);

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

    List<Ingredient> getAllIngredients();

    Map<String, Integer> getTopPaymentMethods();

    Map<Customer, Double> getTopCustomersRevenue(int topWhat);

    Map<Customer, Integer> getTopCustomersOrders(int topWhat);

    List<Order> getOrders(int customerID);

    Customer getCustomer(int customerID);

    void clockIn(int employeeID);

    void clockOut(int employeeID);

    Map<Ingredient, Integer> getTopIngredients(int topWhat);

    Map<Ingredient, Integer> getTopIngredients(Date from, Date to, int topWhat);

    Map<Date, Double> getTopDatesRevenue(int topWhat);

    Map<Date, Integer> getTopDatesTotalOrders(int topWhat);

    List<SaleHistoryItem> getSalesHistory(Date from, Date to);

    List<SaleHistoryItem> getAllTimeSalesHistory();

    void addIngredientStock(int ingredientID, int amount);

    void removeIngredientStock(int ingredientID, int amount);

    List<Order> getOrdersBefore(Date date, int limit);

    List<Order> getOrders(Date from, Date to);

    void addOrder(Order order);

    void addNewIngredientInventory(Ingredient ingredient);

    void updateIngredientInventory(Ingredient ingredient);

    void deleteIngredientInventory(int ingredientID);

    void addEmployee(Employee employee);

    void removeEmployee(int id);

    void updateEmployee(Employee employee);

    void removeMenuItem(int id);

}
