package com.pekings.pos;

import javafx.application.Application;
import javafx.stage.Stage;

public class POSApp extends Application {

    @Override
    public void start(Stage PrimaryStage) throws Exception {
        new Login(PrimaryStage);
    }

    public void initialize(String[] args) {
        launch(args);
    }
}