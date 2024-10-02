package com.pekings.pos;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class POSApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("PeKing POS System");
        stage.setResizable(false);
        //stage.setFullScreen(true);

        Group rootManager = new Group(); // root node
        Scene Manager; //background
        Manager = new Scene(rootManager, 1000, 700); // Style set to 1000x700


        // Create text for text box/buttons etc...
        Text text = new Text();
        text.setText("Manager");
        text.setX(50);
        text.setY(665);

        text.setFont(Font.font("Verdana",18));
        text.setFill(Color.BLACK);
        // text is type of node. need to add this to root node.

        // Make function later.
        // Create login/out button that is rectangle
        // This rectangle will be in the top left, in front of a gray background rectangle scene
        // create new scene
        Rectangle r = new Rectangle(15,15,150,670);
        r.setFill(Color.LIGHTGRAY);

        Button logOutBtn = new Button("_Log Out");
        logOutBtn.setLayoutX(60);
        logOutBtn.setLayoutY(55);

        Button statsBtn= new Button("_Statistics");
        statsBtn.setLayoutX(60);
        statsBtn.setLayoutY(155);

        Button clockInOutBtn = new Button("  _Clock \n In/Out  ");
        clockInOutBtn.setLayoutX(60);
        clockInOutBtn.setLayoutY(195);

        Button inventoryBtn = new Button("_Inventory");
        inventoryBtn.setLayoutX(60);
        inventoryBtn.setLayoutY(255);

        //Popup for logout confirmation/switch to cashier
        Popup logOutPopup = createLogOutPopup(stage);
        logOutBtn.setOnAction(event -> logOutPopup.show(stage));

        //rootManager.getChildren().add();
        rootManager.getChildren().add(r);
        rootManager.getChildren().addAll(logOutBtn,statsBtn,clockInOutBtn,inventoryBtn);
        rootManager.getChildren().add(text);
        //Set scene as Manager node.
        stage.setScene(Manager);
        stage.show();
    }


    private Popup createLogOutPopup(Stage popStage){
        Popup popup = new Popup();

        // Create VBox for popup
        VBox popupContent = new VBox(10);
        popupContent.setPadding(new Insets(10));
        popupContent.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 2px;");

        // Create the label for the confirmation message
        Label confirmationLabel = new Label("Log out or switch accounts");
        confirmationLabel.setFont(Font.font(14));

        // Create the Yes and No buttons
        Button yesBtn = new Button("Log Out");
        Button noBtn = new Button("Cashier");

        // Handle the Yes button (close the app or return to login screen)
        yesBtn.setOnAction(event -> {
            popStage.close(); // Close the application
            popup.hide();  // Hide the popup
        });

        // Handle the No button (just hide the popup)
        noBtn.setOnAction(event -> popup.hide());

        // Create a layout for buttons
        HBox buttonBox = new HBox(10, yesBtn, noBtn);
        buttonBox.setAlignment(Pos.CENTER);

        // Add the label and buttons to the VBox
        popupContent.getChildren().addAll(confirmationLabel, buttonBox);
        popupContent.setAlignment(Pos.CENTER);

        // Add the content to the popup
        popup.getContent().add(popupContent);

        return popup;
    }

    public void initialize(String[] args) {
        launch(args);
    }
}
