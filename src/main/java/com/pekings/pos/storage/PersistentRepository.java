package com.pekings.pos.storage;

import com.pekings.pos.object.Employee;
import com.pekings.pos.object.Ingredient;
import com.pekings.pos.object.MenuItem;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class PersistentRepository implements Repository {

    private Connection conn;
    private QueryLoader queryLoader;

    private static final String DB_USERNAME = "pekings_01";
    private static final String DB_PASSWORD = "uD37k2)R1Kp}";
    private static final String DB_NAME = "csce331_950_pekings_db";
    private static final String DB_ADDRESS = "csce-315-db.engr.tamu.edu";
    private static final String DB_PORT = "5432";

    public void initialize() throws SQLException, IOException, URISyntaxException {
        Properties props = new Properties();
        props.setProperty("user", DB_USERNAME);
        props.setProperty("password", DB_PASSWORD);
        conn = DriverManager.getConnection(
                "jdbc:" + "postgresql" + "://" +
                        DB_ADDRESS + ":" + DB_PORT + "/" + DB_NAME, props);

        queryLoader = new QueryLoader();
        queryLoader.loadQueries();

        if (conn == null || queryLoader == null)
            throw new SQLException("Could not create connection with PostgreSQL server!");
    }

    @Override
    public void addMenuItem(MenuItem menuItem) {
        try {
            Statement statement = conn.createStatement();

            String query = queryLoader.getQuery("add_menu_item")
                    .formatted(menuItem.getName(), menuItem.getPrice());

            for (Ingredient ingredient : menuItem.getIngredients()) {
                String ingredientQuery =
                        "INSERT INTO menu_ingredients (ingredient_id, menu_item, ingredients_in_item) " +
                                "VALUES( " + ingredient.getId() + "," + menuItem.getId() + "," + ingredient.getAmount() + ");";
                statement.addBatch(ingredientQuery);
            }

            statement.addBatch(query);
            statement.executeBatch();

            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeMenuItem(MenuItem menuItem) {

    }

    @Override
    public MenuItem getMenuItem(int id) {
        try {
            Statement statement = conn.createStatement();

            String query = queryLoader.getQuery("get_menu_item")
                    .formatted(id);

            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();

            int menuID = resultSet.getInt("id");
            String name = resultSet.getString("name");
            float price = resultSet.getFloat("price");

            resultSet.close();
            statement.close();

            return new MenuItem(menuID, name, price, getIngredients(menuID));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getDailyIncome() {
        return 0;
    }

    @Override
    public int getWeeklyIncome() {
        return 0;
    }

    @Override
    public int getMonthlyIncome() {
        return 0;
    }

    @Override
    public int getIncome(Date from, Date to) {
        return 0;
    }

    @Override
    public List<Employee> getActiveEmployees() {
        return null;
    }

    @Override
    public List<Employee> getEmployees() {
        return null;
    }

    @Override
    public Set<MenuItem> getMenuItems() {
        try {
            Statement statement = conn.createStatement();

            String query = queryLoader.getQuery("get_menu_items");
            ResultSet resultSet = statement.executeQuery(query);

            Set<MenuItem> menuItems = new HashSet<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("menu_item_id");
                String name = resultSet.getString("menu_item_name");
                float price = resultSet.getFloat("menu_item_price");

                String ingredient_name = resultSet.getString("ingredient_name");
                int quantity = resultSet.getInt("ingredient_quantity");
                int ingredient_batch_price = resultSet.getInt("ingredient_batch_price");
                int ingredient_price = resultSet.getInt("serving_price");
                int ingredient_id = resultSet.getInt("ingredient_id");

                Ingredient ingredient = new Ingredient(ingredient_id, ingredient_name, quantity, ingredient_batch_price, ingredient_price);

                if (menuItems.stream().anyMatch(menuItem -> menuItem.getId() == id)) {
                    MenuItem menuItem = menuItems.stream().filter(menuItem1 -> menuItem1.getId() == id).findAny().orElse(null);
                    assert menuItem != null;
                    menuItem.addIngredient(ingredient);
                } else {
                    MenuItem menuItem = new MenuItem(id, name, price, new ArrayList<>());
                    menuItem.addIngredient(ingredient);
                    menuItems.add(menuItem);
                }
            }

            resultSet.close();
            statement.close();

            return menuItems;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Ingredient getIngredient(int id) {
        try {
            Statement statement = conn.createStatement();

            String query = queryLoader.getQuery("get_ingredient")
                    .formatted(id);

            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();

            int ingredientID = resultSet.getInt("id");
            String name = resultSet.getString("name");
            float servingPrice = resultSet.getFloat("serving_price");
            int amount = resultSet.getInt("amount");
            int price_batch = resultSet.getInt("price_batch");

            resultSet.close();
            statement.close();

            return new Ingredient(ingredientID, name, servingPrice, amount, price_batch);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Ingredient> getIngredients(int menuItemID) {
        try {
            Statement statement = conn.createStatement();

            String query = queryLoader.getQuery("get_menu_item_ingredients")
                    .formatted(menuItemID);
            ResultSet resultSet = statement.executeQuery(query);

            List<Ingredient> ingredients = new ArrayList<>();
            while (resultSet.next()) {
                int ingredientID = resultSet.getInt("ingredient_id");
                Ingredient ingredient = getIngredient(ingredientID);

                ingredients.add(ingredient);
            }

            resultSet.close();
            statement.close();

            return ingredients;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<MenuItem> getTopMenuItems(int topWhat) {
        return null;
    }
}
