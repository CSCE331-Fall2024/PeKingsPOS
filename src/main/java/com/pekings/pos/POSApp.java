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

    Stage PrimaryStage;
    Scene login, cashier;

    Repository repo;
    List<Employee> Employees;

    TextField usernameBox;
    PasswordField passwordBox;
    Button loginBtn;
    Text error;
    Group rootLogin;

    @Override
    public void start(Stage PrimaryStage) throws Exception {
        repo = Main.getRepository();

        Employees = repo.getEmployees();

        for(Employee emp : Employees){
            System.out.println(emp.getUsername() + " : " + emp.getPassword() + " : " + emp.getPosition());
        }



        this.PrimaryStage = PrimaryStage;
        this.PrimaryStage.setTitle("PeKings POS");
        this.PrimaryStage.setResizable(false);
        this.PrimaryStage.setWidth(1000);
        this.PrimaryStage.setHeight(700);
//        this.PrimaryStage.getIcons().add(new Image("file:icon.png"));


        rootLogin = new Group();
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

        usernameBox = new TextField();
        usernameBox.setLayoutX(460);
        usernameBox.setLayoutY(310);
        usernameBox.setOnKeyPressed(e -> {
            if (e.getCode().toString().equals("ENTER")){
                checkLogin();
            }
        });

        Label passwordLabel = new Label("Password: ");
        passwordLabel.setLayoutX(310);
        passwordLabel.setLayoutY(350);
        passwordLabel.setStyle("-fx-font-size: 30px");
        passwordLabel.setTextFill(Color.WHITE);

        passwordBox = new PasswordField();
        passwordBox.setLayoutX(460);
        passwordBox.setLayoutY(360);
        passwordBox.setOnKeyPressed(e -> {
            if (e.getCode().toString().equals("ENTER")){
                checkLogin();
            }
        });

        error = new Text("");
        error.setX(320);
        error.setY(405);
        error.setFill(Color.YELLOW);

        loginBtn = new Button("Log In");
        loginBtn.setLayoutX(440);
        loginBtn.setLayoutY(420);
        loginBtn.setOnAction(e -> checkLogin());

        rootLogin.getChildren().addAll(title, usernameLabel, passwordLabel, usernameBox, passwordBox, loginBtn, error);

        this.PrimaryStage.setScene(login);
        this.PrimaryStage.show();


    }

    private void checkLogin(){
        String username = usernameBox.getText();
        String password = passwordBox.getText();

        for(Employee emp : Employees){
            if((username.equals(emp.getUsername())) && (password.equals(emp.getPassword()))){
                if(emp.getPosition().equals("employee")){
                    Button btn = new Button("Log\nOut");
                    btn.setOnAction(e -> PrimaryStage.setScene(login));
                    Cashier cash = new Cashier(PrimaryStage, btn, emp.getId());
                    cashier = cash.getScene();
                    PrimaryStage.setScene(cashier);
                }else{
                    System.out.println("Manager Login");
                }
                error.setText("");
                break;
            }
            if(emp == Employees.getLast()){
                error.setText("Username or Password was not recognized, please try again.");
            }
        }
        usernameBox.clear();
        passwordBox.clear();

//        Cashier cash = new Cashier(PrimaryStage, login);
//        cashier = cash.getScene();
//        PrimaryStage.setScene(cashier);
    }

    public void initialize() {
        launch();
    }

}