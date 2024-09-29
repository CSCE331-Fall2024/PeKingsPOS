package com.pekings.pos.storage;

import com.pekings.pos.object.*;
import com.pekings.pos.util.ThrowingConsumer;

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
        String addItemQuery = queryLoader.getQuery("add_menu_item")
                .formatted(menuItem.getName(), menuItem.getPrice());

        List<String> queries = new ArrayList<>();
        queries.add(addItemQuery);

        for (Ingredient ingredient : menuItem.getIngredients()) {
            String ingredientQuery = queryLoader.getQuery("add_ingredient")
                    .formatted(ingredient.getId() + "", menuItem.getId() + "", ingredient.getAmount() + "");
            queries.add(ingredientQuery);
        }

        performInsertQuery(queries.toArray(String[]::new));
    }

    @Override
    public MenuItem getMenuItem(int id) {
        // We need an atomic reference or something weird to create an object that isn't
        // inside the consumer, so just make a list and get the first (and only) element
        List<MenuItem> menuItems = new ArrayList<>();
        performFetchQuery("get_menu_item", resultSet -> {
            int menuID = resultSet.getInt("id");
            String name = resultSet.getString("name");
            float price = resultSet.getFloat("price");
            List<Ingredient> ingredients = getIngredients(menuID);
            menuItems.add(new MenuItem(menuID, name, price, ingredients));
        }, id + "");

        return menuItems.getFirst();
    }

    @Override
    public List<MenuItem> getOrderItems(int orderID) {
        List<MenuItem> menuItems = new ArrayList<>();
        performFetchQuery("get_menu_item_order", resultSet -> {
            int menuID = resultSet.getInt("menu_item_id");
            MenuItem menuItem = getMenuItem(menuID);
            menuItems.add(menuItem);
        }, orderID + "");
        return menuItems;
    }

    @Override
    public double getDailyIncome(Date date) {
        Date dayAfter = Date.valueOf(date.toLocalDate().plusDays(1));
        return getIncome(date, dayAfter);
    }

    @Override
    public double getWeeklyIncome(Date startOfWeek) {
        Date weekAfter = Date.valueOf(startOfWeek.toLocalDate().plusDays(7));
        return getIncome(startOfWeek, weekAfter);
    }

    @Override
    public double getMonthlyIncome(Date startOfMonth) {
        Date monthAfter = Date.valueOf(startOfMonth.toLocalDate().plusDays(30));
        return getIncome(startOfMonth, monthAfter);
    }

    @Override
    public double getIncome(Date from, Date to) {
        return getTopMenuItemsRevenue(from, to).values().stream().reduce(Double::sum).orElse(-1D);
    }

    @Override
    public List<Employee> getActiveEmployees() {
        List<Employee> employees = new ArrayList<>();
        performFetchQuery("get_active_employees", resultSet -> {
            Employee employee = makeEmployee(resultSet);
            employees.add(employee);
        });

        return employees;
    }

    @Override
    public List<Employee> getEmployees() {
        List<Employee> employees = new ArrayList<>();
        performFetchQuery("get_all_employees", resultSet -> {
            employees.add(makeEmployee(resultSet));
        });

        return employees;
    }

    @Override
    public Set<MenuItem> getMenuItems() {
        Set<MenuItem> menuItems = new HashSet<>();
        performFetchQuery("get_menu_items", resultSet -> {
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
        });

        return menuItems;
    }

    @Override
    public Ingredient getIngredient(int id) {
        // We need an atomic reference or something weird to create an object that isn't
        // inside the consumer, so just make a list and get the first (and only) element
        List<Ingredient> ingredients = new ArrayList<>();
        performFetchQuery("get_ingredient", resultSet ->
                ingredients.add(makeIngredient(resultSet)),
                id + "");
        return ingredients.getFirst();
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
        List<Ingredient> ingredients = new ArrayList<>();
        performFetchQuery("get_menu_item_ingredients", resultSet -> {
            int ingredientID = resultSet.getInt("ingredient_id");
            Ingredient ingredient = getIngredient(ingredientID);

            ingredients.add(ingredient);
        }, menuItemID + "");

        return ingredients;
    }

    @Override
    public Map<String, Integer> getTopPaymentMethods() {
        Map<String, Integer> map = new HashMap<>();
        performFetchQuery("get_top_payment_methods", resultSet -> {
            String paymentMethod = resultSet.getString("payment_method");
            int total_orders = resultSet.getInt("total_orders");
            map.put(paymentMethod, total_orders);
        });

        return map;
    }

    @Override
    public Map<Customer, Double> getTopCustomersRevenue(int topWhat) {
        Map<Customer, Double> revenueMap = new HashMap<>();
        performFetchQuery("get_most_valuable_customers_revenue", resultSet -> {
            int customerID = resultSet.getInt("customer_id");
            double moneySpent = resultSet.getDouble("money_spent");

            Customer customer = getCustomer(customerID);
            revenueMap.put(customer, moneySpent);
        }, topWhat + "");
        return revenueMap;
    }

    @Override
    public Map<Customer, Integer> getTopCustomersOrders(int topWhat) {
        Map<Customer, Integer> totalOrders = new HashMap<>();
        performFetchQuery("get_most_valuable_customers_revenue", resultSet -> {
            int customerID = resultSet.getInt("customer_id");
            int total_orders = resultSet.getInt("total_orders");

            Customer customer = getCustomer(customerID);
            totalOrders.put(customer, total_orders);
        }, topWhat + "");
        return totalOrders;
    }

    @Override
    public List<Order> getOrders(int customerID) {
        List<Order> orders = new ArrayList<>();
        performFetchQuery("get_orders_from_customer", resultSet -> {
            int orderID = resultSet.getInt("id");
            int customerId = resultSet.getInt("customer_id");
            double price = resultSet.getDouble("price");
            String payment_method = resultSet.getString("payment_method");
            int employeeID = resultSet.getInt("employee_id");
            Date date = resultSet.getDate("order_time");

            Order order = new Order(orderID, customerId, price, payment_method, date, employeeID);

            List<MenuItem> menuItems = getOrderItems(orderID);
            menuItems.forEach(order::addItem);

            orders.add(order);
        }, customerID + "");
        return orders;
    }

    @Override
    public Customer getCustomer(int customerID) {
        Customer customer = new Customer(customerID);
        List<Order> orders = getOrders(customerID);
        orders.forEach(customer::addOrder);
        return customer;
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

    private void performInsertQuery(String... queries) {
        try {
            Statement statement = conn.createStatement();
            for (String query : queries) {
                statement.addBatch(query);
            }

            statement.executeBatch();

            statement.close();
            conn.close();
        } catch (Exception x) {
            throw new RuntimeException(x);
        }
    }
}
