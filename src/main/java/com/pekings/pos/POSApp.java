package com.pekings.pos;

import com.pekings.pos.storage.Repository;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;

public class POSApp extends Application {

    private Repository repo;

    public void initialize() {
        launch();
    }

    public void start(Stage stage) throws Exception {
        new Login(stage);
        repo = Main.getRepository(); // Get repo from Main class
        stage.setTitle("PeKing POS System");

        Manager manager = new Manager(repo);
        stage.setScene(manager.createManagerScene(stage));
        stage.setResizable(false);
        stage.show();
    }

}

