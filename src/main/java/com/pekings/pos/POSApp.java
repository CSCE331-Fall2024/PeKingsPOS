package com.pekings.pos;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

//import java.awt.*;

public class POSApp extends Application {
    @Override

    public void start(Stage stage) throws Exception {
        Stage window = new Stage();
//        window.setTitle("POS");
//        window.setResizable(false);
//        window.show();

        Scene scene = new Scene(createContent(), 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    private Region createContent() {
        //return new Label("Hello World");
        HBox results = new HBox(new Label("Name:"), new TextField(""));
        results.setSpacing(6);
        results.setPadding(new Insets(0,0,0,50));
        results.setAlignment(Pos.CENTER_LEFT);
        return results;
    }

    public void initialize() {
        launch();
    }
}
