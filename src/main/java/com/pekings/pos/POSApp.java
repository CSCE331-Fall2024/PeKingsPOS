package com.pekings.pos;

import javafx.application.Application;
import javafx.stage.Stage;

public class POSApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Stage window = new Stage();
        window.setTitle("POS");
        window.setResizable(false);
        window.show();
    }

    public void initialize() {
        launch();
    }
}
