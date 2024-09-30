package com.pekings.pos;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

    Stage window;
    Scene login;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage PrimaryStage) throws Exception{
        window = PrimaryStage;
        window.setTitle("PeKings POS");

        GridPane grid1 = new GridPane();
        grid1.setPadding(new Insets(10, 10, 10, 10));
        grid1.setVgap(10);
        grid1.setVgap(5);

        Label title = new Label("Manager \nLogin");
        GridPane.setConstraints(title, 0, 1);

        TextField pin = new TextField("PIN");
        GridPane.setConstraints(pin, 1, 1);

        grid1.getChildren().addAll(title, pin);

        login = new Scene(grid1, 1000, 700);
        window.setScene(login);
    }
}