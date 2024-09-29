package com.pekings.pos;

import com.pekings.pos.object.MenuItem;
import com.pekings.pos.storage.PersistentRepository;
import com.pekings.pos.storage.Repository;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.List;

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
