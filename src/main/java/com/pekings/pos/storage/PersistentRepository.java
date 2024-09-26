package com.pekings.pos.storage;

import com.pekings.pos.object.Employee;
import com.pekings.pos.object.Ingredient;
import com.pekings.pos.object.MenuItem;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class PersistentRepository implements Repository {

    private Connection conn;

    private static final String DB_USERNAME = "pekings_01";
    private static final String DB_PASSWORD = "uD37k2)R1Kp}";
    private static final String DB_NAME = "csce331_950_pekings_db";
    private static final String DB_ADDRESS = "csce-315-db.engr.tamu.edu";
    private static final String DB_PORT = "5432";

    public void initialize() throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", DB_USERNAME);
        props.setProperty("password", DB_PASSWORD);
        conn = DriverManager.getConnection(
                "jdbc:" + "postgresql" + "://" +
                        DB_ADDRESS + ":" + DB_PORT + "/" + DB_NAME, props);

        if (conn == null)
            throw new SQLException("Could not create connection with PostgreSQL server!");
    }

    @Override
    public void addMenuItem(MenuItem menuItem) {
        try {
            Statement statement = conn.createStatement();

            String query = "INSERT INTO menu (name, price) " +
                    "VALUES (" + menuItem.getName() + "," + menuItem.getPrice() + ");";

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

            String query = "SELECT * FROM menu WHERE id = " + id + ";";
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

            String query = "SELECT\n" +
                    "    menu.id AS menu_item_id,\n" +
                    "    menu.name AS menu_item_name,\n" +
                    "    menu.price AS menu_item_price,\n" +
                    "    inventory.name AS ingredient_name,\n" +
                    "    menu_ingredients.ingredients_in_item AS ingredient_quantity,\n" +
                    "    inventory.price_batch AS ingredient_batch_price,\n" +
                    "    inventory.serving_price AS serving_price,\n" +
                    "    inventory.id AS ingredient_id\n" +
                    "FROM\n" +
                    "    menu\n" +
                    "        JOIN\n" +
                    "    menu_ingredients ON menu.id = menu_ingredients.menu_item\n" +
                    "        JOIN\n" +
                    "    inventory ON menu_ingredients.ingredient_id = inventory.id;";
            ResultSet resultSet = statement.executeQuery(query);

            Set<MenuItem> menuItems = new HashSet<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("menu_item_id");
                String name = resultSet.getString("menu_item_name");
                float price = resultSet.getFloat("menu_item_price");

                String ingredient_name = resultSet.getString("ingredient_name");
                int quantity = resultSet.getInt("ingredient_quantity");
                //Ingredient ingredient = new Ingredient()
                List<Ingredient> ingredients = getIngredients(id);
                MenuItem menuItem = new MenuItem(id, name, price, ingredients);
                menuItems.add(menuItem);
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

            String query = "SELECT * FROM inventory WHERE id=" + id + ";";
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

            String query = "SELECT * FROM menu_ingredients WHERE id=" + menuItemID + ";";
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
