package com.pekings.pos;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;

import static java.awt.Color.*;

public class POSApp extends Application {

    Stage window;
    Scene login, cashier;

    @Override
    public void start(Stage PrimaryStage) throws Exception {
        window = PrimaryStage;
        window.setTitle("PeKings POS");
        window.setResizable(false);
        window.setWidth(1000);
        window.setHeight(700);

        Group rootLogin = new Group();
        login = new Scene(rootLogin, 1000, 700);
        login.setFill(Color.web("#2F2E2E"));

        Text title = new Text("Employee\n   Login");
        title.setStyle("-fx-font-size: 50px");
        title.setFill(Color.WHITE);
        title.setX(360);
        title.setY(200);

        Label usernameLabel = new Label("Username: ");
        usernameLabel.setLayoutX(310);
        usernameLabel.setLayoutY(300);
        usernameLabel.setStyle("-fx-font-size: 30px");
        usernameLabel.setTextFill(Color.WHITE);

        TextField usernameBox = new TextField("");
        usernameBox.setLayoutX(460);
        usernameBox.setLayoutY(310);

        Label passwordLabel = new Label("Password: ");
        passwordLabel.setLayoutX(310);
        passwordLabel.setLayoutY(350);
        passwordLabel.setStyle("-fx-font-size: 30px");
        passwordLabel.setTextFill(Color.WHITE);

        TextField passwordBox = new TextField("");
        passwordBox.setLayoutX(460);
        passwordBox.setLayoutY(360);

        Button loginBtn = new Button("Log In");
        loginBtn.setLayoutX(440);
        loginBtn.setLayoutY(420);


        rootLogin.getChildren().addAll(title, usernameLabel, passwordLabel, usernameBox, passwordBox, loginBtn);
//        window.setScene(login);

        Group rootCashier = new Group();
        cashier = new Scene(rootCashier, 1000, 700);

        Rectangle leftRect = new Rectangle();
        leftRect.setX(0);
        leftRect.setY(0);
        leftRect.setWidth(150);
        leftRect.setHeight(700);
        leftRect.setFill(Color.web("#D9D9D9"));

        Text cashierText = new Text("Cashier");
        cashierText.setX(50);
        cashierText.setY(640);
        cashierText.setFill(Color.BLACK);

        Button exit = new Button("Log\nOut");
        exit.setPrefWidth(80);
        exit.setPrefHeight(80);
        exit.setStyle("-fx-background-color: Red");
        // Change Font Weight
        exit.setLayoutX(30);
        exit.setLayoutY(30);

        Button newOrder = new Button(" New\nOrder");
        newOrder.setPrefWidth(80);
        newOrder.setPrefHeight(80);
        newOrder.setStyle("-fx-background-color: #36919E");
        newOrder.setLayoutX(30);
        newOrder.setLayoutY(170);

        Button cancelOrder = new Button("Cancel\n Order");
        cancelOrder.setPrefWidth(80);
        cancelOrder.setPrefHeight(80);
        cancelOrder.setStyle("-fx-background-color: #36919E");
        cancelOrder.setLayoutX(30);
        cancelOrder.setLayoutY(280);

        Button viewPrevious = new Button(" View\nPrevious");
        viewPrevious.setPrefWidth(80);
        viewPrevious.setPrefHeight(80);
        viewPrevious.setStyle("-fx-background-color: #36919E");
        viewPrevious.setLayoutX(30);
        viewPrevious.setLayoutY(390);


        rootCashier.getChildren().addAll(leftRect, cashierText, exit, newOrder, cancelOrder, viewPrevious);
        window.setScene(cashier);
        window.show();


    }

    public void initialize() {
        launch();
    }

}