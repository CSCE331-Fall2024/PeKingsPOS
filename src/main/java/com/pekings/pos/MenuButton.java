package com.pekings.pos;

import com.pekings.pos.object.MenuItem;
import com.pekings.pos.util.OrderText;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;

import static javafx.scene.text.TextAlignment.CENTER;

public class MenuButton {
    MenuItem item;
    TilePane pane;
    Cashier cashier;


    public MenuButton(MenuItem item, TilePane pane, Cashier cashier){
        this.item = item;
        this.pane = pane;
        this.cashier = cashier;
    }

    public Button createMenuBtn(){

        Button btn = new Button(item.getName());
        btn.setTextAlignment(CENTER);
        btn.setPrefWidth(100);
        btn.setPrefHeight(100);
        btn.setStyle("-fx-background-color: #BA6433");

        btn.setOnAction(e -> {
            cashier.edited = true;
            new OrderText(cashier, item, pane);
        });

        return btn;
    }
}
