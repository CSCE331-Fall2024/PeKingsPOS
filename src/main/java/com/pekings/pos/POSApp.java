package com.pekings.pos;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import static java.awt.Color.*;

public class POSApp extends Application {

    Stage window;
    Scene login, cashier;
    List<String> menuItems = new ArrayList<>();
    String currOrder = "0";

    @Override
    public void start(Stage PrimaryStage) throws Exception {
        //Fills menuItems
        for(int i = 0; i < 40; i++){
            if(i % 2 == 0){
                menuItems.add("Chickem");
            }else{
                menuItems.add("Beef");
            }
        }


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
        newOrder.setLayoutY(160);

        Button cancelOrder = new Button("Cancel\n Order");
        cancelOrder.setPrefWidth(80);
        cancelOrder.setPrefHeight(80);
        cancelOrder.setStyle("-fx-background-color: #36919E");
        cancelOrder.setLayoutX(30);
        cancelOrder.setLayoutY(255);

        Button viewPrevious = new Button("  View\nPrevious");
        viewPrevious.setPrefWidth(80);
        viewPrevious.setPrefHeight(80);
        viewPrevious.setStyle("-fx-background-color: #36919E");
        viewPrevious.setLayoutX(30);
        viewPrevious.setLayoutY(350);

        Button memoBtn = new Button("Memo");
        memoBtn.setPrefWidth(80);
        memoBtn.setPrefHeight(80);
        memoBtn.setStyle("-fx-background-color: #1E3A63");
        memoBtn.setTextFill(Color.BLACK);
        memoBtn.setLayoutX(30);
        memoBtn.setLayoutY(455);

        Rectangle rectRight = new Rectangle();
        rectRight.setX(700);
        rectRight.setY(0);
        rectRight.setWidth(300);
        rectRight.setHeight(700);
        rectRight.setFill(Color.web("#D9D9D9"));

        Line orderBorderLeft = new Line();
        orderBorderLeft.setStartX(700);
        orderBorderLeft.setStartY(0);
        orderBorderLeft.setEndX(700);
        orderBorderLeft.setEndY(700);
        orderBorderLeft.setStrokeWidth(4);

        Line paymentBorderTop = new Line();
        paymentBorderTop.setStartX(700);
        paymentBorderTop.setStartY(450);
        paymentBorderTop.setEndX(1000);
        paymentBorderTop.setEndY(450);
        paymentBorderTop.setStrokeWidth(2);

        Rectangle paymentRect = new Rectangle();
        paymentRect.setX(700);
        paymentRect.setY(450);
        paymentRect.setWidth(300);
        paymentRect.setHeight(250);
        paymentRect.setFill(Color.web("#8c8c8c"));


        //Menu section
        ScrollPane menuScroll = new ScrollPane();
        menuScroll.setLayoutX(150);
        menuScroll.setLayoutY(0);
        menuScroll.setMaxWidth(600);
        menuScroll.setMaxHeight(700);

        TilePane menuPane = new TilePane();
        menuScroll.setContent(menuPane);

        menuPane.setPrefColumns(4);
        menuPane.setPadding(new Insets(30));
        menuPane.setHgap(30);
        menuPane.setVgap(30);
        menuPane.setLayoutX(180);
        menuPane.setLayoutY(25);
        menuPane.setMaxWidth(600);
        menuPane.setMaxHeight(550);

        //Adds menu items
        for(String i : menuItems){
            menuPane.getChildren().add(createMenuBtn(i));
        }

        Text orderNumTitle = new Text("Order #");
        orderNumTitle.setStyle("-fx-font-size: 30px");
        orderNumTitle.setX(790);
        orderNumTitle.setY(40);
        Text orderNum = new Text(currOrder);
        orderNum.setStyle("-fx-font-size: 40px");
        orderNum.setX(825);
        orderNum.setY(90);

        Line orderNumLine = new Line();
        orderNumLine.setStartX(700);
        orderNumLine.setStartY(110);
        orderNumLine.setEndX(1000);
        orderNumLine.setEndY(110);
        orderNumLine.setStrokeWidth(5);


        //Displays items on current order
        ScrollPane orderScroll = new ScrollPane();
        orderScroll.setLayoutX(700);
        orderScroll.setLayoutY(110);
        orderScroll.setMaxHeight(340);
        orderScroll.setMaxWidth(400);

        TilePane orderPane = new TilePane();
//        orderPane.setLayoutY(50);
        orderPane.setPadding(new Insets(20, 0, 10, 20));
        orderScroll.setContent(orderPane);

        orderPane.setStyle("-fx-background-color: #D9D9D9");
        orderPane.setPrefColumns(2);
        orderPane.setHgap(103);
        orderPane.setVgap(15);
        for(int i = 0; i < 50; i++) {
            Text txt = new Text("BURGERRRRRR");
            orderPane.getChildren().add(txt);

            txt = new Text("$3.99");
            orderPane.getChildren().add(txt);
        }




        rootCashier.getChildren().addAll(leftRect, cashierText, exit, newOrder, cancelOrder, viewPrevious, memoBtn, rectRight, paymentRect);
        rootCashier.getChildren().add(menuScroll);
        rootCashier.getChildren().addAll(orderNumTitle, orderNum, orderNumLine, orderScroll);
        rootCashier.getChildren().addAll(orderBorderLeft, paymentBorderTop);
        window.setScene(cashier);
        window.show();


    }

    Button createMenuBtn(String item){
        Button btn = new Button(item);
        btn.setPrefWidth(100);
        btn.setPrefHeight(100);
        btn.setStyle("-fx-background-color: #BA6433");

        return btn;
    }

    public void initialize() {
        launch();
    }

}