package com.pekings.pos;

import com.pekings.pos.object.Employee;
import com.pekings.pos.object.MenuItem;
import com.pekings.pos.storage.Repository;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.awt.Color.*;

public class POSApp extends Application {

    @Override
    public void start(Stage PrimaryStage) throws Exception {
        Login login = new Login(PrimaryStage);
    }

    public void initialize() {
        launch();
    }

}