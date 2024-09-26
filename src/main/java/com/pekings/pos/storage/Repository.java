package com.pekings.pos.storage;

import com.pekings.pos.object.Employee;
import com.pekings.pos.object.Ingredient;
import com.pekings.pos.object.MenuItem;

import java.sql.Date;
import java.util.List;
import java.util.Set;

public interface Repository {

    void addMenuItem(MenuItem menuItem);

    void removeMenuItem(MenuItem menuItem);

    MenuItem getMenuItem(int id);

    int getDailyIncome();

    int getWeeklyIncome();

    int getMonthlyIncome();

    int getIncome(Date from, Date to);

    List<Employee> getActiveEmployees();

    List<Employee> getEmployees();

    Set<MenuItem> getMenuItems();

    Ingredient getIngredient(int id);

    List<MenuItem> getTopMenuItems(int topWhat);

    List<Ingredient> getIngredients(int menuItemID);

}
