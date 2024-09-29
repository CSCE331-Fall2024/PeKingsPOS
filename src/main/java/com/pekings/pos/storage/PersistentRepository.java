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
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

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
    public double getDailyIncome(Date date) {
        Date dayAfter = (Date) date.clone();
        dayAfter.setTime(TimeUnit.DAYS.toMillis(1));
        return getIncome(date, dayAfter);
    }

    @Override
    public double getWeeklyIncome(Date startOfWeek) {
        Date weekAfter = (Date) startOfWeek.clone();
        weekAfter.setTime(TimeUnit.DAYS.toMillis(7));
        return getIncome(startOfWeek, weekAfter);
    }

    @Override
    public double getMonthlyIncome(Date startOfMonth) {
        Date monthAfter = (Date) startOfMonth.clone();
        monthAfter.setTime(TimeUnit.DAYS.toMillis(7));
        return getIncome(startOfMonth, monthAfter);
    }

    @Override
    public double getIncome(Date from, Date to) {
        return getTopMenuItemsRevenue(from, to).values().stream().reduce(Double::sum).orElse(-1D);
    }

    @Override
    public List<Employee> getActiveEmployees() {
        try {
            Statement statement = conn.createStatement();

            String query = queryLoader.getQuery("get_active_employees");
            ResultSet resultSet = statement.executeQuery(query);

            List<Employee> employees = new ArrayList<>();
            while (resultSet.next()) {
                Employee employee = makeEmployee(resultSet);
                employees.add(employee);
            }

            resultSet.close();
            statement.close();

            return employees;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Employee> getEmployees() {
        try {
            Statement statement = conn.createStatement();

            String query = queryLoader.getQuery("get_all_employees");
            ResultSet resultSet = statement.executeQuery(query);

            List<Employee> employees = new ArrayList<>();
            while (resultSet.next()) {
                employees.add(makeEmployee(resultSet));
            }

            resultSet.close();
            statement.close();

            return employees;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

            Ingredient ingredient = makeIngredient(resultSet);

            resultSet.close();
            statement.close();

            return ingredient;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<MenuItem, Double> getTopMenuItemsRevenue(int topWhat) {
        Map<MenuItem, Double> revenue = new HashMap<>();

        performFetchQuery("get_top_menu_items_revenue", resultSet -> {
            int menu_item_id = resultSet.getInt("menu_item_id");
            double totalRevenue = resultSet.getDouble("total_revenue");

            MenuItem menuItem = getMenuItem(menu_item_id);
            revenue.put(menuItem, totalRevenue);
        }, topWhat + "");

        return revenue;
    }

    @Override
    public Map<MenuItem, Integer> getTopMenuItemsOrders(int topWhat) {
        Map<MenuItem, Integer> orders = new HashMap<>();

        performFetchQuery("get_top_menu_items_orders", resultSet -> {
            int menu_item_id = resultSet.getInt("menu_item_id");
            int totalOrders = resultSet.getInt("total_orders");

            MenuItem menuItem = getMenuItem(menu_item_id);
            orders.put(menuItem, totalOrders);
        }, topWhat + "");

        return orders;
    }

    @Override
    public Map<MenuItem, Double> getTopMenuItemsRevenue(Date from, Date to) {
        Map<MenuItem, Double> revenueMap = new HashMap<>();
        performFetchQuery("get_periodic_revenue", resultSet -> {
            MenuItem menuItem = getMenuItem(resultSet.getInt("menu_item_id"));
            double revenue = resultSet.getDouble("revenue");
            revenueMap.put(menuItem, revenue);
        }, from.toString(), to.toString());

        return revenueMap;
    }

    @Override
    public Map<MenuItem, Integer> getTopMenuItemsOrders(Date from, Date to) {
        Map<MenuItem, Integer> revenueMap = new HashMap<>();
        performFetchQuery("get_periodic_orders", resultSet -> {
            MenuItem menuItem = getMenuItem(resultSet.getInt("menu_item_id"));
            int totalOrders = resultSet.getInt("total_orders");
            revenueMap.put(menuItem, totalOrders);
        }, from.toString(), to.toString());

        return revenueMap;
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

    public Employee makeEmployee(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String username = resultSet.getString("username");
        String pass = resultSet.getString("pass");
        String position = resultSet.getString("position");
        Date date = resultSet.getDate("last_clockin");
        boolean b = resultSet.getBoolean("is_clockedin");
        return new Employee(id, username, pass, position, date, b);
    }

    public Ingredient makeIngredient(ResultSet resultSet) throws SQLException {
        int ingredientID = resultSet.getInt("id");
        String name = resultSet.getString("name");
        float servingPrice = resultSet.getFloat("serving_price");
        int amount = resultSet.getInt("amount");
        int price_batch = resultSet.getInt("price_batch");
        return new Ingredient(ingredientID, name, servingPrice, amount, price_batch);
    }

    private void performFetchQuery(String query, ThrowingConsumer<ResultSet> forEachItem, String... parameters) {
        try {
            Statement statement = conn.createStatement();
            String s = queryLoader.getQuery(query)
                    .formatted(parameters);
            ResultSet resultSet = statement.executeQuery(s);

            while (resultSet.next()) {
                forEachItem.accept(resultSet);
            }

            conn.close();
            resultSet.close();
        } catch (Exception x) {
            throw new RuntimeException(x);
        }
    }
}
