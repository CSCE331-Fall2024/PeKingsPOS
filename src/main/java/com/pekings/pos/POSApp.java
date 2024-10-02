package com.pekings.pos;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
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
        //logOutBtn.set


        //rootManager.getChildren().add();
        rootManager.getChildren().add(r);
        rootManager.getChildren().addAll(logOutBtn,statsBtn,clockInOutBtn,inventoryBtn);
        rootManager.getChildren().add(text);
        //Set scene as Manager node.
        stage.setScene(Manager);
        stage.show();
    }
    public void initialize(String[] args) {
        launch(args);
    }
}
