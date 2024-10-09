package com.pekings.pos;

//import com.pekings.pos.object.Ingredient;
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
import java.util.*;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

public class Manager {

    List<MenuItem> menuItemList;
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
    Button stats = createButton(30,455, " _Stats \nReport", "-fx-background-color: #36919E");


    public Manager(Repository repo) {
        this.repo = repo;
        rootManager = new Pane();
    }


    public Scene createManagerScene(Stage stage) {
        // Setup Manager Scene

        Map<Ingredient, Integer> ingredientIntegerMap = repo.getTopIngredients(50);
        //int ingredientSum = ingredientIntegerMap.values().stream().reduce(Integer::sum).orElse(-1);
        Scene managerScene = new Scene(rootManager, 1000, 700);
        repo = Main.getRepository();
        menuItemList = repo.getMenuItems().stream().sorted(Comparator.comparingInt(value -> (int) value.getId())).toList();

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
                    Button saveButton = new Button("_Save");
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
                        updateMenuItem(item.getId(), newName, newPrice);
                    });
                    deleteButton.setOnAction(_ -> {
                        // Remove from database here
                        removeMenuItem(item.getId());
                        menuItemsContainer.getChildren().remove(itemRow);
                    });

                    itemRow.getChildren().addAll(nameField,priceField,editButton,saveButton,deleteButton);
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
                        long newId = addItem(newName, newPrice);
                        // Refresh the list (you might want to just add the new item instead of refreshing everything)
                        menuItems.fire(); // This will refresh the entire list
                });


                newItemRow.getChildren().addAll(newNameField, newPriceField, addButton);
                menuItemsContainer.getChildren().add(newItemRow);

                ScrollPane scrollPane = new ScrollPane(menuItemsContainer);
                scrollPane.setPrefViewportWidth(650);
                scrollPane.setPrefViewportHeight(685);
                scrollPane.setLayoutX(160);
                scrollPane.setLayoutY(15);

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

            HBox Header = new HBox(20);
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
            for (Ingredient ingredient : ingredientIntegerMap.keySet()) {
                HBox itemRow = new HBox(20);
                TextField nameField = new TextField(ingredient.getName());
                TextField idField = new TextField(String.valueOf(ingredient.getId()));
                TextField quantityField = new TextField(String.valueOf(ingredientIntegerMap.get(ingredient)));
                nameField.setPrefWidth(200);
                idField.setPrefWidth(100);
                quantityField.setPrefWidth(200);

                Button editButton = new Button("_Edit");
                Button saveButton = new Button("_Save All Changes");
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

                    updateMenuItem(ingredient.getId(), newName, newQuantity);
                });
                deleteButton.setOnAction(_ -> {
                    // Remove from database here
                    removeMenuItem(ingredient.getId());
                    inventoryItemsContainer.getChildren().remove(itemRow);
                });

                itemRow.getChildren().addAll(nameField,idField,quantityField,editButton,saveButton,deleteButton);
                inventoryItemsContainer.getChildren().add(itemRow);

            }

            HBox newItemRow = new HBox(20);
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
//                    try {
                String newName = newNameField.getText();
                int newquantity = Integer.parseInt(newquantityField.getText());
                // Add to database here
                //MenuItem newItem = new MenuItem().
//                    long newId = addIngredient(newName, newquantity);
                //public void addMenuItem(MenuItem menuItem)
                //repo.addMenuItem();
                // Refresh the list (you might want to just add the new item instead of refreshing everything)
                menuItems.fire(); // This will refresh the entire list
//                    } catch (NumberFormatException ex) {
//                        showAlert("Invalid price format. Please enter a valid number.");
//                    }
            });


            newItemRow.getChildren().addAll(newNameField, newIdField, newquantityField, addButton);
            inventoryItemsContainer.getChildren().add(newItemRow);

            ScrollPane scrollPane = new ScrollPane(inventoryItemsContainer);
            scrollPane.setPrefViewportWidth(650);
            scrollPane.setPrefViewportHeight(685);
            scrollPane.setLayoutX(160);
            scrollPane.setLayoutY(15);

            rootManager.getChildren().add(scrollPane);
        });

        employees.setOnAction(_ -> {
            rootManager.getChildren().clear();
            rootManager.getChildren().addAll(r,text,logOut,menuItems,inventory,employees,stats);
        });

        //Menu section
        centerScroll.setLayoutX(160);
        centerScroll.setLayoutY(0);
        centerScroll.setPrefWidth(550);
        centerScroll.setPrefHeight(700);

        rootManager.getChildren().addAll(r,text,logOut,menuItems,inventory,employees,stats);


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

        contentBox.getChildren().add(initChart);

        HBox buttonBox = new HBox(10);
        buttonBox.setPrefWidth(845);
        buttonBox.setLayoutX(155);
        buttonBox.setLayoutY(15);

        Button topMenuItemsRevenueBtn = new Button("Top Menu Items (Total Revenue)");
        Button topMenuItemsOrdersBtn = new Button("Top Menu Items (# of Orders)");
        Button dailyIncomeBtn = new Button("Daily Income");
        Button topPaymentMethodsBtn = new Button("Top Payment Methods");

        topMenuItemsRevenueBtn.setOnAction(_ -> updateChart(createTopMenuItemsRevenueChart()));
        topMenuItemsOrdersBtn.setOnAction(_ -> updateChart(createTopMenuItemsOrdersChart()));
        dailyIncomeBtn.setOnAction(_ -> updateChart(createDailyIncomeChart()));
        topPaymentMethodsBtn.setOnAction(_ -> updateChart(createTopPaymentMethodsChart()));

        buttonBox.getChildren().addAll(topMenuItemsRevenueBtn, topMenuItemsOrdersBtn, dailyIncomeBtn, topPaymentMethodsBtn);
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

    private BarChart<String, Number> createTopMenuItemsOrdersChart() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> chart = new BarChart<>(xAxis, yAxis);

        Map<MenuItem, Integer> topItems = repo.getTopMenuItemsOrders(5);
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        topItems.forEach((item, orders) ->
                    series.getData().addAll(new XYChart.Data<>(item.getName(), orders))
        );

        chart.getData().add(series);
        chart.setTitle("Top 5 Menu Items by Number of Orders");
        xAxis.setLabel("Menu Item");
        yAxis.setLabel("Number of Orders");
        return chart;
    }

    private LineChart<String, Number> createDailyIncomeChart() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        LineChart<String, Number> chart = new LineChart<>(xAxis, yAxis);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -6); // Start from 7 days ago

        for (int i = 0; i < 7; i++) {
            Date date = (Date) cal.getTime();
            double income = repo.getDailyIncome(new java.sql.Date(date.getTime()));
            series.getData().add(new XYChart.Data<>(formatDate(date), income));
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }

        chart.getData().add(series);
        chart.setTitle("Daily Income - Last 7 Days");
        xAxis.setLabel("Date");
        yAxis.setLabel("Income");
        return chart;
    }

    private PieChart createTopPaymentMethodsChart() {
        PieChart chart = new PieChart();
        Map<String, Integer> topMethods = repo.getTopPaymentMethods();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(topMethods.entrySet().stream().map(entry -> new PieChart.Data(entry.getKey(), entry.getValue())).collect(Collectors.toList()));
        chart.setData(pieChartData);
        chart.setTitle("Top Payment Methods");
        return chart;
    }

    private String formatDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return String.format("%02d/%02d", cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
    }



    // update remove and add functions
    private void updateMenuItem(long id, String newName, float newPrice) {
        // Update the menu item in the database
        // You need to implement this method based on your database structure
        //updateMenuItem(item.getId(), newName, newPrice);

        //Will need removeMenuItem and addItem to update
    }

    private void removeMenuItem(long id) {
        // Remove the menu item from the database
        // You need to implement this method based on your database structure
    }

    private long addItem(String name, float price) {
        // Add a new menu item to the database and return its ID
        // You need to implement this method based on your database structure
        //addMenuItem(MenuItem menuItem)
        //long newId;

        //repo.addMenuItem();
        return -1; // Placeholder return
    }

    private long addIngredient(String name, int quantity){

        return -1;
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


    private Popup createLogOutPopup(Stage popStage) {
        Popup popup = new Popup();

        // Create VBox for popup
        VBox popupContent = new VBox(10);
        popupContent.setPadding(new Insets(10));
        popupContent.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 2px;");


        Label confirmationLabel = new Label("Log out or switch accounts");
        confirmationLabel.setFont(Font.font(14));


        Button yesBtn = new Button("Log Out");
        Button noBtn = new Button("Cashier");


        yesBtn.setOnAction(Lambda -> {
            popStage.close(); // Close the application
            popup.hide();  // Hide the popup
        });

        noBtn.setOnAction(Lambda -> popup.hide());

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


//    public void displayMenuItems() {
//        // Fetch the menu items from the database
//        List<MenuItem> menuItems = getMenuItems(); // Implement this method
//
//        // Create the ScrollPane with the menu items
//        ScrollPane menuScrollPane = MenuItemsPane(menuItems);
//
//        // Clear existing nodes and add the new ScrollPane
//        rootManager.getChildren().clear();
//        rootManager.getChildren().add(menuScrollPane);
//    }

}


    // Possible charts. May remove later
//    public void createPieChart() {
//        Map<Ingredient, Integer> ingredients = repo.getTopIngredients(4);
//        PieChart pieChart = new PieChart();
//        Vector<String> name = new Vector<>();
//        Vector<Float> percentage = new Vector<>();
//        int sum = ingredients.values().stream().reduce(Integer::sum).orElse(-1);
//
//        try {
//            if (sum == -1){
//                throw new IllegalArgumentException("Invalid input!");
//            }
//
//        } catch (IllegalArgumentException e) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Error");
//            alert.setContentText(e.getMessage());
//            alert.showAndWait();
//
//            return;
//        }
//
//        int num = 0;
//        for (Ingredient ingredient : ingredients.keySet()) {
//            int amount = ingredients.get(ingredient);
//            float perc = (float) (amount * 100.0 / sum);
//            PieChart.Data slice = new PieChart.Data(ingredient.getName(), perc);
//            pieChart.getData().add(slice);
//            num++;
//        }
//
//        pieChart.setTranslateX(150);
//        pieChart.setTranslateY(50);
//        pieChart.setPrefSize(400, 400);
//        rootManager.getChildren().add(pieChart);
//    }
//}
