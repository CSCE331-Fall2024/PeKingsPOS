package com.pekings.pos;

import com.pekings.pos.object.MenuItem;
import com.pekings.pos.storage.Repository;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
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

    Stage loginStage;
    Scene login, cashier;


    @Override
    public void start(Stage PrimaryStage) throws Exception {
//        repo = Main.getRepository();

        loginStage = PrimaryStage;
        loginStage.setTitle("PeKings POS");
        loginStage.setResizable(false);
        loginStage.setWidth(1000);
        loginStage.setHeight(700);
//        loginStage.getIcons().add(new Image("file:icon.png"));


        Group rootLogin = new Group();
        login = new Scene(rootLogin, 1000, 700);
        login.setFill(Color.web("#2F2E2E"));

        Text title = new Text("Employee\n   Login");
        title.setStyle("-fx-font-size: 50px");
        title.setFill(Color.WHITE);
        title.setX(360);
        title.setY(200);

        Label usernameLabel = new Label("Username: ");
        usernameLabel.setLayoutX(310);
        usernameLabel.setLayoutY(300);
        usernameLabel.setStyle("-fx-font-size: 30px");
        usernameLabel.setTextFill(Color.WHITE);

        TextField usernameBox = new TextField("");
        usernameBox.setLayoutX(460);
        usernameBox.setLayoutY(310);

        Label passwordLabel = new Label("Password: ");
        passwordLabel.setLayoutX(310);
        passwordLabel.setLayoutY(350);
        passwordLabel.setStyle("-fx-font-size: 30px");
        passwordLabel.setTextFill(Color.WHITE);

        TextField passwordBox = new TextField("");
        passwordBox.setLayoutX(460);
        passwordBox.setLayoutY(360);

        Button exitBtn = new Button();
        exitBtn.setOnAction(e -> loginStage.setScene(login));

        Button loginBtn = new Button("Log In");
        loginBtn.setLayoutX(440);
        loginBtn.setLayoutY(420);
        loginBtn.setOnAction(e -> {
            Cashier cash = new Cashier(PrimaryStage);
            cashier = cash.getScene();
            loginStage.setScene(cashier);
        });

        rootLogin.getChildren().addAll(title, usernameLabel, passwordLabel, usernameBox, passwordBox, loginBtn);

        loginStage.setScene(login);
        loginStage.show();
    }

//    public void switchSceneLogin(){
//        loginStage.setScene(login);
//    }

    public void initialize() {
        launch();
    }

}