package com.pekings.pos;

import com.pekings.pos.object.MenuItem;
import com.pekings.pos.util.OrderText;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;

/** * Returns a button linked to a menu item. On click, it'll add the menu item to the order and display it.
 *
 * @author MenuButton creates all functionality for the menu button given any menu item
 * @param  item   an item from the menu item database
 * @param  name  the TilePane you'll add the text to
 * @param cashier the instance of the cashier screen you'll be editing information in
 */

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
            new OrderText(cashier, item, pane);
        });

        return btn;
    }
}
