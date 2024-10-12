package com.pekings.pos;

import com.pekings.pos.object.Employee;
import com.pekings.pos.object.Ingredient;
import com.pekings.pos.object.MenuItem;
import com.pekings.pos.storage.Repository;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.collections.ObservableList;
import javafx.scene.text.Text;

import javafx.stage.Popup;
import javafx.stage.Stage;

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

    public final Map<String, Boolean> checkBoxStates = new HashMap<>();

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

        menuItems.setOnAction(_ -> openMenuItems(stage));
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
                    Ingredient newIngredient = new Ingredient(ingredient.getId(),newName, ingredient.getPrice(), (int)newQuantity, (ingredient.getPrice() * newQuantity));
                    repo.updateIngredientInventory(newIngredient);
                    saveButton.setVisible(false);
                    editButton.setVisible(true);
                    nameField.setEditable(false);
                    quantityField.setEditable(false);
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
            TextField newPriceField = new TextField();

            TextField newquantityField = new TextField();
            Button addButton = new Button("Add Item");

            newNameField.setPromptText("New item name");
            newquantityField.setPromptText("quantity");
            newNameField.setPrefWidth(200);
            newIdField.setPromptText("Auto Generated ID");
            newquantityField.setPrefWidth(200);
            newPriceField.setPromptText("Set Price");
            newPriceField.setPrefWidth(100);

            addButton.setOnAction(_ -> {
                String newName = newNameField.getText();
                int newPrice = Integer.valueOf(newPriceField.getText());
                int newQuantity = Integer.parseInt(newquantityField.getText());
                addIngredient(newName,newPrice, newQuantity);

                inventory.fire(); // This will refresh the entire list
            });

            newItemRow.getChildren().addAll(newNameField, newIdField, newquantityField, newPriceField, addButton);
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
                    updateEmployee(employee.getId(), usernameField.getText(), passwordField.getText(), employee.getPosition(), employee.getLastClockIn(), employee.isClockedIn());
                });
                deleteButton.setOnAction(_ -> {
                    // Remove from database here
                    Popup employeePopup = createDeletePopupEmployee(employeeContainer, itemRow, employee);
                    employeePopup.show(stage);

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

                // Add to database here user, pass, pos, lastClockIn, clockedIn = false
                Time lstIn = new Time(0,0,0);

                Employee newGuy = new Employee((employeeList.getLast().getId()+1),newUser,newPass,"Employee",lstIn, false);
                repo.addEmployee(newGuy);
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
        openMenuItems(stage);

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

        Map<MenuItem, Integer> topItems = repo.getTopMenuItemsOrders(5).entrySet()
                .stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue())) // Sorting in descending order
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1, // If there are duplicate keys, keep the first one (though duplicates shouldn't happen here)
                        LinkedHashMap::new // Collecting into a LinkedHashMap to preserve order
                ));

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
        Employee newguy = new Employee(id, username, password, position, lastClockIn, clockedIn);
        repo.updateEmployee(newguy);
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

    private Popup createDeletePopup(VBox container, HBox itemRow, MenuItem item){
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
                repo.deleteMenuItem((int) item.getId());
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
    private Popup createDeletePopupEmployee(VBox container, HBox itemRow, Employee item){
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
                repo.removeEmployee((int) item.getId());
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

    private List<String> createAddMenuItemPopup(List<String> ingredientNames) {
        Popup popup = new Popup();
        List<String> newIngrList = new ArrayList<>();
        popup.setHeight(500);
        popup.setWidth(500);
        // Create VBox for popup
        VBox popupContent = new VBox(10);
        popupContent.setPadding(new Insets(10));
        popupContent.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 2px;");


        Button done = new Button();
        Button clear = new Button();

        done.setOnAction(_ ->{

        });
        HBox buttonBox = new HBox(10,done, clear);
        buttonBox.setAlignment(Pos.CENTER);

        popupContent.getChildren().addAll(buttonBox);
        popupContent.setAlignment(Pos.CENTER);

        popup.getContent().add(popupContent);

        return newIngrList;
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

    private void openMenuItems(Stage stage){
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
                String newPriceStr = priceField.getText();
                float newPrice = Float.parseFloat(newPriceStr.substring(1));

//                  System.out.println("MenuItem edited");
                int tempIdHold = (int) item.getId();
                List<Ingredient> tempIngredients = repo.getIngredients(tempIdHold);
//                    repo.removeMenuItem((int) item.getId());
                menuItemsContainer.getChildren().remove(itemRow);
                MenuItem newOne = new MenuItem(tempIdHold, newName, newPrice, tempIngredients, true);
//                    repo.addMenuItem(newOne);
                repo.updateMenuItem(newOne);

                saveButton.setVisible(false);
                editButton.setVisible(true);
                nameField.setEditable(false);
                priceField.setEditable(false);
                System.out.println("MenuItem edited Finish");
            });
            deleteButton.setOnAction(_ -> {
                // Remove from database here
                Popup dlt = createDeletePopup(menuItemsContainer,itemRow,item);
                dlt.show(stage);
                //menuItemsContainer.getChildren().remove(itemRow);
                openMenuItems(stage);
            });

            itemRow.getChildren().addAll(nameField, priceField, editButton, saveButton, deleteButton);
            menuItemsContainer.getChildren().add(itemRow);


        }


        HBox newItemRow = new HBox(10);
        TextField newNameField = new TextField();
        TextField newPriceField = new TextField();
        Button selectIngredients = new Button("Ingredients");
        Button addButton = new Button("Add Item");

        newNameField.setPromptText("New item name");
        newPriceField.setPromptText("Price");
        newNameField.setPrefWidth(300);
        newPriceField.setPrefWidth(100);

        selectIngredients.setOnAction(_ -> showSelectionDialog());

        addButton.setOnAction(_ -> {
            String name = newNameField.getText();
            if(name.isEmpty()){
                System.out.println("Name is empty");
                return;
            }

            String priceString = newPriceField.getText();
            if(priceString.isEmpty()){
                System.out.println("Price is empty");
                return;
            }else if(priceString.startsWith("$")){
                priceString = priceString.substring(1);
            }
            float price = Float.parseFloat(priceString);
            MenuItem newMenuItem = new MenuItem(-1, name, price, getIngredients(), true);
//            repo.addMenuItem(newMenuItem);
            openMenuItems(stage); // This will refresh the entire list
        });


        newItemRow.getChildren().addAll(newNameField, newPriceField, selectIngredients, addButton);
        menuItemsContainer.getChildren().add(newItemRow);

        // Adds space so the add item buttons display fully
        Region extraSpace = new Region();
        extraSpace.setPrefHeight(50);
        VBox.setVgrow(extraSpace, javafx.scene.layout.Priority.ALWAYS);
        menuItemsContainer.getChildren().add(extraSpace);

        ScrollPane scrollPane = new ScrollPane(menuItemsContainer);
        scrollPane.setPrefWidth(650);
        scrollPane.setPrefHeight(700);
        scrollPane.setLayoutX(160);
        scrollPane.setLayoutY(0);

        checkBoxStates.clear();
        rootManager.getChildren().add(scrollPane);
    }

    private List<Ingredient> getIngredients(){
        List<String> ingredientStrings = new ArrayList<>();
        List<Ingredient> ingredients = new ArrayList<>();
        for (Map.Entry<String, Boolean> entry : checkBoxStates.entrySet()) {
            if(entry.getValue()){
                ingredientStrings.add(entry.getKey());
            }
        }
        System.out.println(ingredientStrings);

        return ingredients;
    }

    private void showSelectionDialog() {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Select Items");

        VBox dialogContent = new VBox(10, new Label("Select the items:"));
        List<Ingredient> ingredients = repo.getAllIngredients();
//        CheckBox[] checkBoxes = new CheckBox[ingredients.size()];
        SelectedIngredientsBox[] ingredientsBoxes = new SelectedIngredientsBox[ingredients.size()];

        for(int i = 0; i < ingredients.size(); i++) {
            SelectedIngredientsBox box = new SelectedIngredientsBox(ingredients.get(i), this);
//            checkBoxes[i] = box.getCheckBox();
            ingredientsBoxes[i] = box;
            dialogContent.getChildren().add(box.getCheckBox());
        }




        ScrollPane scrollPane = new ScrollPane(dialogContent);
        scrollPane.setFitToWidth(true); // Make the scroll pane fit the dialog width
        scrollPane.setPrefHeight(200); // Set a fixed height for the scroll pane

        dialog.getDialogPane().setContent(scrollPane);

        // Add a button to close the dialog
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);

        // Set a preferred width for the dialog
        dialog.getDialogPane().setPrefWidth(400);

        // Show the dialog and wait for a result
        dialog.showAndWait();

        for (SelectedIngredientsBox box : ingredientsBoxes) {
            checkBoxStates.put(box.ingredient.getName(), box.getCheckBox().isSelected());
        }
//        System.out.println(checkBoxStates.size());
    }
}
