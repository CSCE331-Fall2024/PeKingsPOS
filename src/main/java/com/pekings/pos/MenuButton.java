package com.pekings.pos;

import com.pekings.pos.object.MenuItem;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import static javafx.scene.text.TextAlignment.CENTER;

public class MenuButton {
    MenuItem item;

    public MenuButton(MenuItem item){
        this.item = item;
    }

    Button createMenuBtn(){

        Button btn = new Button(item.getName());
        btn.setTextAlignment(CENTER);
        btn.setPrefWidth(100);
        btn.setPrefHeight(100);
        btn.setStyle("-fx-background-color: #BA6433");

        return btn;
    }
}
