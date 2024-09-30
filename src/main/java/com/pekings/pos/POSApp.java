package com.pekings.pos;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;

import static java.awt.Color.*;

public class POSApp extends Application {

    Stage window;
    Scene login;

    @Override
    public void start(Stage PrimaryStage) throws Exception{
        window = PrimaryStage;
        window.setTitle("PeKings POS");
        window.setResizable(false);
        window.setWidth(1000);
        window.setHeight(700);

        Group root = new Group();
        Scene login = new Scene(root, 1000, 700);
        login.setFill(Color.web("#2F2E2E"));

        Text title = new Text("Employee Login");
        title.setStyle("-fx-font-size: 50px");
        title.setFill(Color.WHITE);
        title.setX(290);
        title.setY(250);

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



//        StackPane rootPane = new StackPane();
//        login = new Scene(rootPane, 1000, 700);
//        Pane titlePane = new Pane();
//        Pane loginPane = new Pane();
//        rootPane.getChildren().addAll(titlePane, loginPane);
//
//        Label title = new Label("Login");
//        title.setAlignment(Pos.CENTER);
//        titlePane.getChildren().add(title);
//        titlePane.set



//        GridPane grid1 = new GridPane();
//        grid1.setPadding(new Insets(10, 10, 10, 10));
//        grid1.setVgap(10);
//        grid1.setHgap(2);

//        Label title = new Label("Login");
//        title.setStyle("-fx-font-size: 50px");
//        GridPane.setConstraints(title, 0, 0);
//
//        Label usernameLabel = new Label("Username: ");
//        GridPane.setConstraints(usernameLabel, 0, 5);
//
//        TextField usernameLogin = new TextField("");
//        GridPane.setConstraints(usernameLogin, 2, 5);
//
//        Label passwordLabel = new Label("Password: ");
//        GridPane.setConstraints(passwordLabel, 0, 6);
//
//        TextField passwordLogin = new TextField("");
//        GridPane.setConstraints(passwordLogin, 2, 6);
//
//        grid1.getChildren().addAll(title, usernameLabel, usernameLogin, passwordLabel, passwordLogin);
//        grid1.setAlignment(Pos.CENTER);

//        login = new Scene(grid1, 1000, 700);
//        window.setScene(login);

        root.getChildren().addAll(title, usernameLabel, passwordLabel, usernameBox, passwordBox);
        window.setScene(login);
        window.show();
    }

    public void initialize() {
        launch();
    }

//    private final StringProperty greeting = new SimpleStringProperty("");
//    private final StringProperty name = new SimpleStringProperty("");
//
//    @Override
//    public void start(Stage primaryStage){
//        Scene scene = new Scene(createContents(), 1200, 700);
//        scene.setFill(Color.web("#81c483"));
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }
//
//    private Region createContents() {
//        VBox results = new VBox(20, createText(), createInputRow(), createOutputLabel(), createButton());
////        results.setAlignment(Pos.CENTER);
//        String css = getClass().getResource("/css/application.css").toExternalForm();
//        results.getStylesheets().add(css);
//
//        return results;
//    }
//
//    private Text createText(){
//        Text text = new Text("Manager \nLogin");
//        text.getStyleClass().add("manager-text");
////        text.setTextAlignment(TextAlignment.CENTER);
//        return text;
//    }
//
//    private Button createButton() {
//        Button results = new Button("Hello");
//        results.setOnAction(evt -> setGreeting());
////        results.setAlignment(Pos.CENTER);
//        return results;
//    }
//
//    private HBox createInputRow() {
//        TextField textField = new TextField("");
//        textField.textProperty().bindBidirectional(name);
//        Label namePrompt = new Label("Name:");
//        namePrompt.getStyleClass().add("prompt-label");
//        HBox hBox = new HBox(6, namePrompt, textField);
//        hBox.setAlignment(Pos.CENTER);
//        return hBox;
//    }
//
//    private Node createOutputLabel() {
//        Label results = new Label("");
//        results.getStyleClass().add("greeting-label");
//        results.textProperty().bind(greeting);
//        return results;
//    }
//
//    private void setGreeting() {
//        greeting.set("Hello " + name.get());
//    }
}
