package com.pekings.pos;

import com.pekings.pos.object.MenuItem;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
            Text txt = new Text(item.getName());
            pane.getChildren().add(txt);

            updateTotals();

            String price = String.valueOf(item.getPrice());
            int size = price.length();
            if(price.charAt(size - 1) == '.'){
                price += "00";
            }else if(price.charAt(size - 2) == '.'){
                price += "0";
            }
            price = "$" + price;
            txt = new Text(price);
            pane.getChildren().add(txt);
        });

        return btn;
    }

    private void updateTotals(){
        cashier.sub += item.getPrice();

        String subtotal = String.valueOf(cashier.sub);
        int size = subtotal.length();
        if(subtotal.charAt(size - 1) == '.'){
            subtotal += "00";
        }else if(subtotal.charAt(size - 2) == '.'){
            subtotal += "0";
        }
        cashier.subTotalTxt.setText("Sub-Total: $" + subtotal);

        String tax = String.valueOf((Math.round((cashier.sub * 0.0625) * 100) / 100.00));
        size = tax.length();
        if(tax.charAt(size - 1) == '.'){
            tax += "00";
        }else if(tax.charAt(size - 2) == '.'){
            tax += "0";
        }
        cashier.taxTxt.setText("Tax: $" + tax);

        String total = String.valueOf((Math.round((cashier.sub * 1.0625) * 100) / 100.00));
        size = total.length();
        if(total.charAt(size - 1) == '.'){
            total += "00";
        }else if(total.charAt(size - 2) == '.'){
            total += "0";
        }
        cashier.totalTxt.setText("Total: $" + total);
    }
}
