package com.pekings.pos;

import com.pekings.pos.object.MenuItem;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import static javafx.scene.text.TextAlignment.CENTER;

public class MenuButton {
    MenuItem item;
    TilePane pane;
    Cashier cashier;
    boolean clicked = false;

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
            Text txt = new Text(item.getName());
//            txt.setStyle("-fx-background-color: Red");
            pane.getChildren().add(txt);

            cashier.updateTotals(item.getPrice());

            String price = String.valueOf(item.getPrice());
            int size = price.length();
            if(price.charAt(size - 1) == '.'){
                price += "00";
            }else if(price.charAt(size - 2) == '.'){
                price += "0";
            }
            price = "$" + price;
            Text priceTxt = new Text(price);
            pane.getChildren().add(priceTxt);

            cashier.orderItems.add(item);

            txt.setOnMouseClicked(m ->
            {
                if(!clicked) {
                    txt.setFill(Color.DARKBLUE);
                    priceTxt.setFill(Color.DARKBLUE);

                    cashier.deleteText.add(txt);
                    cashier.deleteText.add(priceTxt);

                    cashier.deleteOrderItems.add(item);
                }else{
                    txt.setFill(Color.BLACK);
                    priceTxt.setFill(Color.BLACK);

                    cashier.deleteText.remove(txt);
                    cashier.deleteText.remove(priceTxt);

                    cashier.deleteOrderItems.remove(item);
                }
                clicked = !clicked;
            });
        });

        return btn;
    }
}
