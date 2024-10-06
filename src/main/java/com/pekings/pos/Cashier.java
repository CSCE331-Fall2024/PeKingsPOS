package com.pekings.pos;

import com.pekings.pos.object.MenuItem;
import com.pekings.pos.storage.Repository;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
//import jdk.dynalink.Operation;

import java.rmi.server.Operation;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

public class Cashier {
    static int currOrder = 0;
//    Set<MenuItem> menuItems;
    List<MenuItem> menuItems;
    private Repository repo;
    Scene cashier, login;

    double sub = 0.00;
    Text subTotalTxt;
    Text taxTxt;
    Text totalTxt;


    public Cashier(Stage PrimaryStage){
        login = PrimaryStage.getScene();
        repo = Main.getRepository();
        menuItems = repo.getMenuItems().stream().sorted(Comparator.comparingInt(value -> (int) value.getId())).toList();
        currOrder++;

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
        exit.setText("Log\nOut");
        exit.setPrefWidth(80);
        exit.setPrefHeight(80);
        exit.setStyle("-fx-background-color: Red");
        // Change Font Weight
        exit.setLayoutX(30);
        exit.setLayoutY(30);
        exit.setOnAction(e -> PrimaryStage.setScene(login));

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
//        for (MenuItem i : menuItems) {
//            MenuButton MenuItemButton = new MenuButton(i, PrimaryStage);
//            menuPane.getChildren().add(MenuItemButton.createMenuBtn());
//        }

        Text orderNumTitle = new Text("Order #");
        orderNumTitle.setStyle("-fx-font-size: 30px");
        orderNumTitle.setX(790);
        orderNumTitle.setY(40);
        Text orderNum = new Text(String.valueOf(currOrder));
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
        orderScroll.setMinHeight(340);
        orderScroll.setMinWidth(400);

        TilePane orderPane = new TilePane();
//        orderPane.setLayoutY(50);
        orderPane.setPadding(new Insets(20, 0, 10, 20));
        orderScroll.setContent(orderPane);

        orderPane.setStyle("-fx-background-color: #D9D9D9");
        orderPane.setPrefColumns(2);
        orderPane.setHgap(20);
        orderPane.setVgap(15);
        orderPane.setPrefTileWidth(140);
        orderPane.setMinHeight(340);
//        orderPane.setPrefTileWidth(80);
//        for (int i = 0; i < 5; i++) {
//            Text txt = new Text("BURGERRRRRR");
//            orderPane.getChildren().add(txt);
//
//            txt = new Text("$3.99");
//            orderPane.getChildren().add(txt);
//        }

        //Adds the TilePane to every menu button
        for (MenuItem i : menuItems) {
            MenuButton MenuItemButton = new MenuButton(i, orderPane, this);
            menuPane.getChildren().add(MenuItemButton.createMenuBtn());
        }


        String subtotal = String.valueOf(sub);
        int size = subtotal.length();
        if(subtotal.charAt(size - 1) == '.'){
            subtotal += "00";
        }else if(subtotal.charAt(size - 2) == '.'){
            subtotal += "0";
        }

        subTotalTxt = new Text("Sub-Total: $" + subtotal);
        subTotalTxt.setX(730);
        subTotalTxt.setY(490);
        subTotalTxt.setStyle("-fx-font-size: 15px");


        String tax = String.valueOf((Math.round((sub * 0.0625) * 100) / 100.00));
        size = tax.length();
        if(tax.charAt(size - 1) == '.'){
            tax += "00";
        }else if(tax.charAt(size - 2) == '.'){
            tax += "0";
        }

        taxTxt = new Text("Tax: $" + tax);
        taxTxt.setX(730);
        taxTxt.setY(520);
        taxTxt.setStyle("-fx-font-size: 15px");


        String total = String.valueOf((Math.round((sub * 1.0625) * 100) / 100.00));
        size = total.length();
        if(total.charAt(size - 1) == '.'){
            total += "00";
        }else if(total.charAt(size - 2) == '.'){
            total += "0";
        }

        totalTxt = new Text("Total: $" + total);
        totalTxt.setX(730);
        totalTxt.setY(600);
        totalTxt.setStyle("-fx-font-size: 30px");

        Button payment = new Button("Payment");
        payment.setLayoutX(700);
        payment.setLayoutY(624);
        payment.setPrefWidth(300);
        payment.setPrefHeight(40);


        rootCashier.getChildren().addAll(leftRect, cashierText, exit, newOrder, cancelOrder, viewPrevious, memoBtn, rectRight, paymentRect);
        rootCashier.getChildren().add(menuScroll);
        rootCashier.getChildren().addAll(orderNumTitle, orderNum, orderNumLine, orderScroll);
        rootCashier.getChildren().addAll(payment, orderBorderLeft, paymentBorderTop, subTotalTxt, taxTxt, totalTxt);
    }

    public Scene getScene(){
        return cashier;
    }

    private void switchScene(){
//        cashier = cash.getScene();
//        loginStage.setScene(cashier);
//        loginStage.show();
    }
}
