package com.pekings.pos;

//import com.pekings.pos.object.Ingredient;
import com.pekings.pos.object.Employee;
import com.pekings.pos.object.Ingredient;
import com.pekings.pos.object.MenuItem;
import com.pekings.pos.storage.Repository;
//import com.pekings.pos.object.Order;
//import javafx.beans.property.BooleanProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.*;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.control.ScrollPane;
//import javafx.scene.layout.TilePane;
import javafx.scene.shape.Rectangle;
//import javafx.scene.Group;
import javafx.scene.Scene;
//import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.control.TextField;
import javafx.collections.ObservableList;
import javafx.scene.text.Text;

import javafx.stage.Popup;
import javafx.stage.Stage;

//import java.awt.*;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

public class Manager {
    Stage PrimaryStage;
    Scene loginScreen;

    List<MenuItem> menuItemList;
    List<Employee> employeeList;
//    List<Ingredient> ingredientList;

    private Repository repo;
    private Pane rootManager;
    ScrollPane centerScroll = new ScrollPane();
    Rectangle r = new Rectangle();
    Text text = new Text("Manager");

    Button logOut = new Button();

    Button menuItems = createButton(30, 160, "_Menu\nItems", "-fx-background-color: #36919E");
    Button inventory = createButton(30, 255, "_Inventory", "-fx-background-color: #36919E");
    Button employees = createButton(30, 350, "_Employees", "-fx-background-color: #36919E");
    Button stats = createButton(30, 455, " _Stats \nReport", "-fx-background-color: #36919E");
    boolean deleteBool = false;

    public Manager(Stage PrimaryStage, Scene loginScreen, Repository repo) {
        this.PrimaryStage = PrimaryStage;
        this.loginScreen = loginScreen;
        this.repo = repo;
        rootManager = new Pane();
    }


    public Scene createManagerScene(Stage stage) {
        // Setup Manager Scene

        List<Ingredient> ingredientList = repo.getAllIngredients();
        //int ingredientSum = ingredientIntegerMap.values().stream().reduce(Integer::sum).orElse(-1);
        Scene managerScene = new Scene(rootManager, 1000, 700);
        repo = Main.getRepository();
        menuItemList = repo.getMenuItems().stream().sorted(Comparator.comparingInt(value -> (int) value.getId())).toList();
        employeeList = repo.getEmployees().stream().sorted(Comparator.comparingInt(value -> (int) value.getId())).toList();
        // Text for Manager Screen

        text.setX(50);
        text.setY(660);
        text.setFill(Color.BLACK);

        // Style left rectangle
        r.setX(0);
        r.setY(0);
        r.setWidth(150);
        r.setHeight(700);
        r.setFill(Color.web("#D9D9D9"));

        // logOut Btn Initialization
        logOut = createLogOutButton(stage);
        // Button actions

        menuItems.setOnAction(_ -> { // Create vertical box that lines up perfectly the names, quantity, and prices of each menu item.

            rootManager.getChildren().clear();
            rootManager.getChildren().addAll(r, text, logOut, menuItems, inventory, employees, stats);

            // Create main container for menu items
            VBox menuItemsContainer = new VBox(10); // 10 is the spacing between items
            menuItemsContainer.setPadding(new Insets(15, 15, 15, 15));

            HBox Header = new HBox(10);
            Label nameColumn = new Label("Name");
            Label priceColumn = new Label("Price");
            nameColumn.setStyle("-fx-font-weight: bold");
            priceColumn.setStyle("-fx-font-weight: bold");
            nameColumn.setPrefWidth(300);
            priceColumn.setPrefWidth(100);
            Header.getChildren().addAll(nameColumn, priceColumn);
            menuItemsContainer.getChildren().add(Header);

            // Add menu items to the list and display them
            for (MenuItem item : menuItemList) {
                HBox itemRow = new HBox(10);
                TextField nameField = new TextField(item.getName());
                TextField priceField = new TextField(String.format("$%.2f", item.getPrice()));
                nameField.setPrefWidth(300);
                priceField.setPrefWidth(100);

                Button editButton = new Button("_Edit");
                Button saveButton = new Button("_Save Changes");
                Button deleteButton = new Button("_Delete");

                saveButton.setVisible(false);
                nameField.setEditable(false);
                priceField.setEditable(false);

                editButton.setOnAction(_ -> {
                    saveButton.setVisible(true);
                    nameField.setEditable(true);
                    priceField.setEditable(true);
                    editButton.setVisible(false);

                });


                saveButton.setOnAction(_ -> {
                    String newName = nameField.getText();
                    float newPrice = Float.parseFloat(priceField.getText());

                    // now update the database with edited information
                    // delete current menu item then add the edited version in
                    // void addMenuItem(MenuItem menuItem);
                    //
                    //repo.addMenuItem(new MenuItem(-1, newName, newPrice, ingredients));

//                    updateMenuItem((int) item.getId(), newName, newPrice);
                    saveButton.setVisible(false);
                    editButton.setVisible(true);
                });
                deleteButton.setOnAction(_ -> {
                    // Remove from database here
                    Popup dlt = createDeletePopup(menuItemsContainer,itemRow);
                    dlt.show(stage);
                    //menuItemsContainer.getChildren().remove(itemRow);


                });

                itemRow.getChildren().addAll(nameField, priceField, editButton, saveButton, deleteButton);
                menuItemsContainer.getChildren().add(itemRow);


            }


            HBox newItemRow = new HBox(10);
            TextField newNameField = new TextField();
            TextField newPriceField = new TextField();
            Button addButton = new Button("Add Item");

            newNameField.setPromptText("New item name");
            newPriceField.setPromptText("Price");
            newNameField.setPrefWidth(300);
            newPriceField.setPrefWidth(100);

            addButton.setOnAction(_ -> {
                String newName = newNameField.getText();
                float newPrice = Float.parseFloat(newPriceField.getText());
                // Add to database here
                //List<Ingredient> newList = createNewIngredientList();
                //MenuItem newMenuItem = new MenuItem(newName,newPrice, newList);
                // newMenuItem = new MenuItem(-1, newName, newPrice,)
                //repo.addMenuItem(newName, newPrice);
                // Refresh the list (you might want to just add the new item instead of refreshing everything)
                menuItems.fire(); // This will refresh the entire list
            });


            newItemRow.getChildren().addAll(newNameField, newPriceField, addButton);
            menuItemsContainer.getChildren().add(newItemRow);

            ScrollPane scrollPane = new ScrollPane(menuItemsContainer);
            scrollPane.setPrefViewportWidth(650);
            scrollPane.setPrefViewportHeight(685);
            scrollPane.setLayoutX(160);
            scrollPane.setLayoutY(0);

            rootManager.getChildren().add(scrollPane);
        });
        PieChart initialChart = createTopMenuItemsRevenueChart();


        stats.setOnAction(_ -> displayStatsReport(initialChart));

        //List<Ingredient> ingredientList;
        // Same thing with different values for Inventory
        inventory.setOnAction(_ -> {
            rootManager.getChildren().clear();
            rootManager.getChildren().addAll(r, text, logOut, menuItems, inventory, employees, stats);

            // Create main container for menu items
            VBox inventoryItemsContainer = new VBox(10); // 10 is the spacing between items
            inventoryItemsContainer.setPadding(new Insets(15, 15, 15, 15));

            HBox Header = new HBox(10);
            Label nameColumn = new Label("Ingredient Name");
            Label idColumn = new Label("IDs");
            Label quantityColumn = new Label("Quantity");
            nameColumn.setStyle("-fx-font-weight: bold");
            idColumn.setStyle("-fx-font-weight: bold");
            quantityColumn.setStyle("-fx-font-weight: bold");
            nameColumn.setPrefWidth(200);
            idColumn.setPrefWidth(100);
            quantityColumn.setPrefWidth(100);
            Header.getChildren().addAll(nameColumn, idColumn, quantityColumn);
            inventoryItemsContainer.getChildren().add(Header);


            // Add menu items to the list and display them
            for (Ingredient ingredient : ingredientList) {
                HBox itemRow = new HBox(10);
                TextField nameField = new TextField(ingredient.getName());
                TextField idField = new TextField(String.valueOf(ingredient.getId()));
                TextField quantityField = new TextField(String.valueOf(ingredient.getAmount()));
                nameField.setPrefWidth(200);
                idField.setPrefWidth(100);
                quantityField.setPrefWidth(200);

                Button editButton = new Button("_Edit");
                Button saveButton = new Button("_Save Changes");
                Button deleteButton = new Button("_Delete");

                saveButton.setVisible(false);
                nameField.setEditable(false);
                idField.setEditable(false);
                quantityField.setEditable(false);

                editButton.setOnAction(_ -> {
                    saveButton.setVisible(true);
                    nameField.setEditable(true);
                    quantityField.setEditable(true);
                    editButton.setVisible(false);

                });

                saveButton.setOnAction(_ -> {
                    String newName = nameField.getText();
                    float newQuantity = Integer.parseInt(quantityField.getText());

                    // now update the database with edited information
                    // delete current menu item then add the edited version in
                    // void addMenuItem(MenuItem menuItem);

//                    updateMenuItem(ingredient.getId(), newName, newQuantity);
                    saveButton.setVisible(false);
                    editButton.setVisible(true);
                });
                deleteButton.setOnAction(_ -> {
                    // Remove from database here
                    Popup dltIngredient = createDeletePopupIngredient(inventoryItemsContainer,itemRow);
                    dltIngredient.show(stage);
                    //TODO Add remove Ingredient method once added to repo
                    //repo.removeIngredientStock((int)ingredient.getId(),ingredient.getAmount());
                });

                itemRow.getChildren().addAll(nameField, idField, quantityField, editButton, saveButton, deleteButton);
                inventoryItemsContainer.getChildren().add(itemRow);

            }

            HBox newItemRow = new HBox(10);
            TextField newNameField = new TextField();
            TextField newIdField = new TextField();
            newIdField.setEditable(false);
            TextField newquantityField = new TextField();
            Button addButton = new Button("Add Item");

            newNameField.setPromptText("New item name");
            newquantityField.setPromptText("quantity");
            newNameField.setPrefWidth(200);
            newIdField.setPromptText("Generated ID");
            newquantityField.setPrefWidth(200);

            addButton.setOnAction(_ -> {
                String newName = newNameField.getText();
                int newQuantity = Integer.parseInt(newquantityField.getText());
                //repo.addIngredient(newName, newQuantity);
                menuItems.fire(); // This will refresh the entire list

            });


            newItemRow.getChildren().addAll(newNameField, newIdField, newquantityField, addButton);
            inventoryItemsContainer.getChildren().add(newItemRow);

            ScrollPane scrollPane = new ScrollPane(inventoryItemsContainer);
            scrollPane.setPrefViewportWidth(650);
            scrollPane.setPrefViewportHeight(685);
            scrollPane.setLayoutX(160);
            scrollPane.setLayoutY(0);

            rootManager.getChildren().add(scrollPane);
        });

        employees.setOnAction(_ -> {
            rootManager.getChildren().clear();
            rootManager.getChildren().addAll(r, text, logOut, menuItems, inventory, employees, stats);
            //public List<Employee> getEmployees() {
            // public List<Employee> getActiveEmployees() {
            // Pull all the data. In each iteration, check if the id of employee matches active employee.
            // If it does, display Green Text, or Green Oval with "Active" in it to the right of the employee name
            // Create main container for employees
            VBox employeeContainer = new VBox(10); // 10 is the spacing between items
            employeeContainer.setPadding(new Insets(15, 15, 15, 15));

            HBox Header = new HBox(10);
            Label nameColumn = new Label("Employee User");
            Label passwordColumn = new Label("Password");
            Label employeeIDColumn = new Label("ID");
            Label employeePosColumn = new Label("Position");
            Label statusColumn = new Label("Active Status");
            nameColumn.setStyle("-fx-font-weight: bold");
            passwordColumn.setStyle("-fx-font-weight: bold");
            employeeIDColumn.setStyle("-fx-font-weight: bold");
            employeePosColumn.setStyle("-fx-font-weight: bold");
            statusColumn.setStyle("-fx-font-weight: bold");
            nameColumn.setPrefWidth(250);
            passwordColumn.setPrefWidth(250);
            employeeIDColumn.setPrefWidth(100);
            employeePosColumn.setPrefWidth(250);
            statusColumn.setPrefWidth(100);
            Header.getChildren().addAll(nameColumn, passwordColumn, employeeIDColumn, employeePosColumn, statusColumn);
            employeeContainer.getChildren().add(Header);
            // Add menu items to the list and display them
            //if edit is clicked. Go back through all employees and set save, and delete to visible
            for (Employee employee : employeeList) {
                HBox itemRow = new HBox(10);
                TextField usernameField = new TextField(employee.getUsername());
                TextField passwordField = new TextField(employee.getPassword());
                TextField employeeIDField = new TextField(String.valueOf(employee.getId()));
                TextField positionField = new TextField(employee.getPosition());
                TextField activeStatusField = new TextField("N/A");
                if (employee.isClockedIn()) {
                    activeStatusField = new TextField("Clocked In");
                } else {
                    activeStatusField = new TextField("Not Clocked In");
                }

                usernameField.setPrefWidth(250);
                passwordField.setPrefWidth(250);
                employeeIDField.setPrefWidth(100);
                positionField.setPrefWidth(250);
                activeStatusField.setPrefWidth(100);


                Button saveButton = new Button("_Save Changes");
                Button deleteButton = new Button("_Remove Employee");
                Button clockedInOut = new Button("_Clock In/Out");
                Button editButton = new Button("_Edit");

                saveButton.setVisible(false);
                usernameField.setEditable(false);
                passwordField.setEditable(false);
                clockedInOut.setVisible(false);
                employeeIDField.setEditable(false);
                activeStatusField.setEditable(false);

                editButton.setOnAction(_ -> {
                    saveButton.setVisible(true);
                    usernameField.setEditable(true);
                    passwordField.setEditable(true);
                    clockedInOut.setVisible(true);
                    updateEmployee(employee.getId(), usernameField.getText(), passwordField.getText(), employee.getPosition(), employee.getLastClockIn(), employee.isClockedIn());
                });

                saveButton.setOnAction(_ -> {
                    String newName = usernameField.getText();

                    // now update the database with edited information
                    // delete current menu item then add the edited version in
                    // void addMenuItem(MenuItem menuItem);
                    //
                    //updateMenuItem((int)item.getId(), newName, );
                    saveButton.setVisible(false);
                    editButton.setVisible(true);
                });
                deleteButton.setOnAction(_ -> {
                    // Remove from database here

                    //removeMenuItem((int) item.getId());
                    employeeContainer.getChildren().remove(itemRow);
                });


                itemRow.getChildren().addAll(usernameField, passwordField, employeeIDField, positionField, activeStatusField, editButton, clockedInOut, saveButton, deleteButton);
                employeeContainer.getChildren().add(itemRow);

            }


            //                TextField usernameField = new TextField(employee.getUsername());
            //                TextField passwordField = new TextField(employee.getPassword());
            //                TextField employeeIDField = new TextField(String.valueOf(employee.getId()));
            //                TextField positionField = new TextField(employee.getPosition());
            //                TextField activeStatusField = new TextField("N/A");

            HBox newItemRow = new HBox(10);
            TextField newUsernameField = new TextField();
            TextField newPasswordField = new TextField();
            TextField newEmployeeIDField = new TextField();
            TextField newPositionField = new TextField();
            TextField newActiveStatusField = new TextField("Not Clocked In");
            Button addButton = new Button("Add Item");

            newUsernameField.setPromptText("Set New Username");
            newPasswordField.setPromptText("Set New Password");
            newEmployeeIDField.setPrefWidth(100);
            newPositionField.setPrefWidth(250);
            newActiveStatusField.setPrefWidth(100);

            addButton.setOnAction(_ -> {
                String newUser = newUsernameField.getText();
                String newPass = newPasswordField.getText();

                // Add to database here
                //addNewEmployee((-1), newUser, newPass,);
                // Refresh the list (you might want to just add the new item instead of refreshing everything)
                menuItems.fire(); // This will refresh the entire list
            });


            newItemRow.getChildren().addAll(newUsernameField, newPasswordField, newEmployeeIDField, newPositionField, newActiveStatusField, addButton);
            employeeContainer.getChildren().add(newItemRow);

            ScrollPane scrollPane = new ScrollPane(employeeContainer);
            scrollPane.setPrefViewportWidth(650);
            scrollPane.setPrefViewportHeight(685);
            scrollPane.setLayoutX(160);
            scrollPane.setLayoutY(15);

            rootManager.getChildren().add(scrollPane);
        });

        //Menu section
        centerScroll.setLayoutX(160);
        centerScroll.setLayoutY(0);
        centerScroll.setPrefWidth(550);
        centerScroll.setPrefHeight(700);

        rootManager.getChildren().addAll(r, text, logOut, menuItems, inventory, employees, stats);


        return managerScene;
    }

    private void displayStatsReport(PieChart initChart) {
        rootManager.getChildren().clear();
        rootManager.getChildren().addAll(r, text, logOut, menuItems, inventory, employees, stats);

        ScrollPane mainScrollPane = new ScrollPane();
        mainScrollPane.setLayoutX(150);
        mainScrollPane.setLayoutY(0);
        mainScrollPane.setPrefWidth(850);
        mainScrollPane.setPrefHeight(700);

        VBox contentBox = new VBox(10);
        Label hoverLabel = new Label();
        for (PieChart.Data data : initChart.getData()) {
            data.getNode().setOnMouseEntered(_ -> {
                hoverLabel.setText(data.getName() + ": " + (int) data.getPieValue());
            });

            data.getNode().setOnMouseExited(_ -> {
                hoverLabel.setText(""); // Clear label when mouse exits
            });
        }
        contentBox.getChildren().addAll(initChart,hoverLabel);

        HBox buttonBox = new HBox(10);
        buttonBox.setPrefWidth(845);
        buttonBox.setLayoutX(100);
        buttonBox.setLayoutY(15);

        Button topMenuItemsRevenueBtn = new Button("Top Menu Items (Total Revenue)");
        Button topMenuItemsOrdersBtn = new Button("Top Menu Items (# of Orders)");
        Button dailyIncomeBtn = new Button("Daily Income");

        topMenuItemsRevenueBtn.setOnAction(_ -> updateChart(createTopMenuItemsRevenueChart()));
        topMenuItemsOrdersBtn.setOnAction(_ -> updateChart(createTopMenuItemsOrdersChart()));
        dailyIncomeBtn.setOnAction(_ -> updateChart(createDailyIncomeChart()));

        buttonBox.getChildren().addAll(topMenuItemsRevenueBtn, topMenuItemsOrdersBtn, dailyIncomeBtn);
        buttonBox.setAlignment(Pos.CENTER);

        contentBox.getChildren().add(buttonBox);

        mainScrollPane.setContent(contentBox);
        rootManager.getChildren().add(mainScrollPane);
    }

    private void updateChart(Chart newChart) {
        // This is to hold whatever chart was last in the order

        VBox contentBox = (VBox) ((ScrollPane) rootManager.getChildren().get(rootManager.getChildren().size() - 1)).getContent();
        contentBox.getChildren().set(0, newChart);
    }

    private PieChart createTopMenuItemsRevenueChart() {
        PieChart chart = new PieChart();
        Map<MenuItem, Double> topItems = repo.getTopMenuItemsRevenue(5);
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                topItems.entrySet().stream()
                        .map(entry -> new PieChart.Data(entry.getKey().getName(), entry.getValue()))
                        .collect(Collectors.toList())
        );

        chart.setData(pieChartData);
        chart.setTitle("Top 5 Menu Items by Revenue");
        return chart;
    }

    private LineChart<String, Number> createTopMenuItemsOrdersChart() {
        // Create the axes for the LineChart
        CategoryAxis xAxis = new CategoryAxis();  // Menu items on X-axis
        NumberAxis yAxis = new NumberAxis();      // Number of orders on Y-axis
        xAxis.setLabel("Menu Item");
        yAxis.setLabel("Number of Orders");

        // Create the LineChart
        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Top 5 Menu Items by Number of Orders");

        // Retrieve the top menu items by number of orders
        Map<MenuItem, Integer> topItems = repo.getTopMenuItemsOrders(5);

        // Create a data series for the chart
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Number of Orders");

        // Populate the series with the data from the map
        topItems.forEach((item, orders) ->
                series.getData().add(new XYChart.Data<>(item.getName(), orders))
        );

        // Add the series to the chart
        lineChart.getData().add(series);

        return lineChart;
    }

    private LineChart<String, Number> createDailyIncomeChart() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel("Date");
        yAxis.setLabel("Revenue $");

        yAxis.setLowerBound(0); // minimum revenue value
        yAxis.setUpperBound(1000); // maximum revenue value

        // Create a LineChart
        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Revenue for Last 30 Days");

        // Create data series
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Daily Revenue");


        Map<Date, Double> revenueData = repo.getTopDatesRevenue(30);


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        System.out.println("Get Data");
        //revenueData is probably enpty :(
        for (Map.Entry<Date, Double> entry : revenueData.entrySet()) {
            String dateStr = dateFormat.format(entry.getKey());
            System.out.println("Date: " + dateStr);

            Double revenue = entry.getValue();
            series.getData().add(new XYChart.Data<>(dateStr, revenue));
        }


        lineChart.getData().add(series);
        return lineChart;
    }


    private String formatDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return String.format("%02d/%02d", cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
    }


    // update remove and add functions
    private void updateMenuItem(long id, String newName, float newPrice, List<Ingredient> ingredients) {
        // Update the menu item in the database
        // You need to implement this method based on your database structure
        //updateMenuItem(item.getId(), newName, newPrice);

        //Will need removeMenuItem and addItem to update
        // TODO Needs updateMenuItem(), deletion and add shouldn't be necessary if update is implemented
        // TODO: Function obsolete if it calls a repo function and just passes in current arguments
//        repo.removeMenuItem(id);
//        repo.addMenuItem(new MenuItem(id, newName, newPrice, ingredients));
//        repo.updateMenuItem(id, newName, newPrice);
    }
// TODO Finish Popup for AddMenuItem Ingredient list
//    private List<Ingredient> createNewIngredientList(){
//        List<Ingredient> newList;
//        Popup makeList = new Popup();
//
//        VBox popupContent = new VBox(10);
//        popupContent.setPadding(new Insets(10));
//        popupContent.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 2px;");
//
//
//        Label prompt = new Label("Make new list of ingredients. \nSeparate each ingredient by a ',' with no spaces, capitalizing the first letter");
//        prompt.setFont(Font.font(14));
//
//
//        TextField ingredientText = new TextField();
//        ingredientText.setPromptText("Ingredients");
//        Button done = new Button("Done");
//        Button noBtn = new Button("Cancel");
//
//
//        noBtn.setOnAction(_ ->
//            makeList.hide()  // Hide the popup
//        );
//
//
//        VBox buttonBox = new VBox(10, ingredientText, noBtn);
//        buttonBox.setAlignment(Pos.CENTER);
//
//        popupContent.getChildren().addAll(prompt, buttonBox);
//        popupContent.setAlignment(Pos.CENTER);
//
//        makeList.getContent().add(popupContent);
//        done.setOnAction(_ -> {
//            makeList.hide();
//            String parse = ingredientText.getText();
//            // Assuming `parse` is the input string
//            String[] words = parse.split(",\\s*");  // Split by comma followed by any number of spaces
//
//            // Create an ArrayList to store the words
//            List<String> wordList = new ArrayList<>(Arrays.asList(words));
//            //public Ingredient(long id, String name, float price, int amount, float batchPrice)
//            for(int i = 0; i < wordList.size(); i++){
//                if(wordList.isEmpty()){
//                    break;
//                }
//                //Ingredient newIngredient = new Ingredient(-1, wordList.get(i), )
//                //newList.get(i) = wordList.get(i);
//            }
//
//
//
//        });
//
//        return newList;
//    }

    // TODO Needs addNewIngredient() in repo
    private long addIngredient(String name, float price, int quantity) {
        Ingredient ingredient = new Ingredient(-1, name, price, quantity, (price * quantity));
//        repo.addIngredientStock(ID, quantity)
//        repo.addNewIngredient(ingredient);

        return -1;
    }

    // TODO Once 2 repo functions are implemented, add them to update Employee
    private void updateEmployee(long id, String username, String password, String position, Time lastClockIn, boolean clockedIn) {
        //Function to be implemented
        // Employee newEmployee = new Employee(id, username, password, position,lastClockIn,clockedIn);
        //repo.removeEmployee(id)
        // AND repo.addEmployee(newEmployee)

        deleteEmployee(id);
//        repo.addEmployee(id, username, password, position, false);
    }

    // TODO add addEmployee(Employee newEmployee) in repo
    private void addNewEmployee(String username, String password, String position) {
        // long id = -1;
        // boolean clockedIn = false;
        // Employee newEmployee = new Employee(id, username, password,position,lastClockIn,clockedIn);
        // Same here: repo.addEmployee(newEmployee)

//        repo.addEmployee(-1, username, password, position, false);
    }

    // TODO add removeEmployee(id) in repo
    private void deleteEmployee(long id) {
//        repo.removeEmployee(id);
    }

    private Button createLogOutButton(Stage stage) {
        Button logOut = new Button("_Log\nOut");
        logOut.setStyle("-fx-background-color: red;");
        logOut.setPrefHeight(80);
        logOut.setPrefWidth(80);
        logOut.setLayoutX(30);
        logOut.setLayoutY(30);
        logOut.setTextFill(Color.BLACK);

        logOut.setOnMouseEntered(_ -> {
            logOut.setStyle("-fx-background-color: darkgray;");
        });
        logOut.setOnMouseExited(_ -> {
            logOut.setStyle("-fx-background-color: RED");
        });

        Popup logOutPopup = createLogOutPopup(stage);
        logOut.setOnAction(_ -> {
            logOutPopup.show(stage);
        });

        return logOut;
    }

    private Popup createDeletePopup(VBox container, HBox itemRow){
        Popup popup = new Popup();

        // Create VBox for popup
        VBox popupContent = new VBox(10);
        popupContent.setPadding(new Insets(10));
        popupContent.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 2px;");


        Label confirmationLabel = new Label("Completely delete this entry?");
        confirmationLabel.setFont(Font.font(14));


        TextField deleteMsg = new TextField();
        deleteMsg.setPromptText("Type DELETE");
        Button noBtn = new Button("Cancel");
        Button done = new Button("Done");

        done.setOnAction(_ -> {
            if (Objects.equals(deleteMsg.getText(), "DELETE")) {
                //repo.deleteMenuItem((int) item.getId());
                container.getChildren().remove(itemRow);
                popup.hide();
            }
            else{
                deleteMsg.clear();
                confirmationLabel.setText("Incorrect Input");
            }
        });

        noBtn.setOnAction(_ -> popup.hide());


        HBox box = new HBox(10,done, noBtn);
        box.setAlignment(Pos.CENTER);

        VBox buttonBox = new VBox(10, deleteMsg, box);
        buttonBox.setAlignment(Pos.CENTER);

        popupContent.getChildren().addAll(confirmationLabel, buttonBox, box);
        popupContent.setAlignment(Pos.CENTER);

        popup.getContent().add(popupContent);

        return popup;
    }

    private Popup createDeletePopupIngredient(VBox container, HBox itemRow){
        Popup popup = new Popup();

        // Create VBox for popup
        VBox popupContent = new VBox(10);
        popupContent.setPadding(new Insets(10));
        popupContent.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 2px;");


        Label confirmationLabel = new Label("Completely delete this entry?");
        confirmationLabel.setFont(Font.font(14));


        TextField deleteMsg = new TextField();
        deleteMsg.setPromptText("Type DELETE");
        Button noBtn = new Button("Cancel");
        Button done = new Button("Done");

        done.setOnAction(_ -> {
            if (Objects.equals(deleteMsg.getText(), "DELETE")) {
                //repo.deleteIngredientItem( item.getId());
                container.getChildren().remove(itemRow);
                popup.hide();
            }
            else{
                deleteMsg.clear();
                confirmationLabel.setText("Incorrect Input");
            }
        });

        noBtn.setOnAction(_ -> popup.hide());


        HBox box = new HBox(10,done, noBtn);
        box.setAlignment(Pos.CENTER);

        VBox buttonBox = new VBox(10, deleteMsg, box);
        buttonBox.setAlignment(Pos.CENTER);

        popupContent.getChildren().addAll(confirmationLabel, buttonBox, box);
        popupContent.setAlignment(Pos.CENTER);

        popup.getContent().add(popupContent);

        return popup;
    }

    private Popup createLogOutPopup(Stage popStage) {
        Popup popup = new Popup();

        // Create VBox for popup
        VBox popupContent = new VBox(10);
        popupContent.setPadding(new Insets(10));
        popupContent.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 2px;");


        Label confirmationLabel = new Label("Return to log in screen?");
        confirmationLabel.setFont(Font.font(14));


        Button yesBtn = new Button("Log Out");
        Button noBtn = new Button("Cancel");

        yesBtn.setOnAction(_ -> {
            PrimaryStage.setScene(loginScreen);
            popup.hide();  // Hide the popup
        });

        noBtn.setOnAction(_ -> popup.hide());

        HBox buttonBox = new HBox(10, yesBtn, noBtn);
        buttonBox.setAlignment(Pos.CENTER);

        popupContent.getChildren().addAll(confirmationLabel, buttonBox);
        popupContent.setAlignment(Pos.CENTER);

        popup.getContent().add(popupContent);

        return popup;
    }


    private Button createButton(int x, int y, String label, String color) {
        Button Btn = new Button(label);
        Btn.setStyle(color);
        Btn.setTextFill(Color.BLACK);
        Btn.setLayoutX(x);
        Btn.setLayoutY(y);
        Btn.setPrefWidth(80);
        Btn.setPrefHeight(80);
        Btn.setOnMouseEntered(_ -> {
            Btn.setStyle("-fx-background-color: darkgray;");
        });
        Btn.setOnMouseExited(_ -> {
            Btn.setStyle(color);
        });

        return Btn;
    }
}
