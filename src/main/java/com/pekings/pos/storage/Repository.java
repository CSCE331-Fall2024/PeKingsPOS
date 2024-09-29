package com.pekings.pos.storage;

import com.pekings.pos.object.Employee;
import com.pekings.pos.object.Ingredient;
import com.pekings.pos.object.MenuItem;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Repository {

    void addMenuItem(MenuItem menuItem);

    MenuItem getMenuItem(int id);

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

}
