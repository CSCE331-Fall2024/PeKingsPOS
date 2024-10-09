package com.pekings.pos;

import com.pekings.pos.storage.Repository;
import javafx.application.Application;
import javafx.stage.Stage;

public class POSApp extends Application {

    private Repository repo;

    public void start(Stage PrimaryStage) throws Exception {
        new Login(PrimaryStage);
    }

    public void initialize(String[] args) {
        launch(args);
    }
}

