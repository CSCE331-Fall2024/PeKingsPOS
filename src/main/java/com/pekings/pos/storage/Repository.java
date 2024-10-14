package com.pekings.pos.storage;

import com.pekings.pos.object.*;
import com.pekings.pos.util.SaleHistoryItem;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Repository {

    /**
     * Adds a menu item to the DB. The required
     * MenuItem must have a list of ingredients ready to
     * go as well as their IDs.
     * You may omit the ID field in MenuItem, but not everywhere else
     *
     * @param menuItem Complete menu item object
     */
    void addMenuItem(MenuItem menuItem);

    /**
     * Deletes a menu item. This method also deletes
     * entries in tables dependent to menu items.
     * This does not delete orders that contain the item,
     * but it can cause these orders to have "ghost items"
     *
     * @param menuItemID Complete menu item object
     */
    void deleteMenuItem(int menuItemID);

    /**
     * Returns a menu item based on its id
     *
     * @param itemID - The ID of the requested item
     * @return - Filled out MenuItem object
     */
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
