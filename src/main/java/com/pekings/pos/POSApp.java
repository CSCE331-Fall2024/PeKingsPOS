package com.pekings.pos;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;

public class POSApp extends Application {

    private Repository repo;

    @Override
    public void start(Stage PrimaryStage) throws Exception {
        new Login(PrimaryStage);
    }

    public void initialize() {
        launch();
        }
    public void start(Stage stage) throws Exception {
        repo = Main.getRepository(); // Get repo from Main class
        stage.setTitle("PeKing POS System");

        Manager manager = new Manager(repo);
        stage.setScene(manager.createManagerScene(stage));
        stage.setResizable(false);
        stage.show();
    }

}

