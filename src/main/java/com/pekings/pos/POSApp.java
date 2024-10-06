package com.pekings.pos;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.chart.PieChart;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class POSApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("PeKing POS System");
        stage.setResizable(false);
        //stage.setFullScreen(true);

        Group rootManager = new Group(); // root node
        Scene Manager; //background
        Manager = new Scene(rootManager, 1000, 700); // Style set to 1000x700


        // Create text for text box/buttons etc...
        Text text = new Text();
        text.setText("Manager");
        text.setX(50);
        text.setY(665);

        text.setFont(Font.font("Verdana",18));
        text.setFill(Color.BLACK);
        // text is type of node. need to add this to root node.

        // Make function later.
        // Create login/out button that is rectangle
        // This rectangle will be in the top left, in front of a gray background rectangle scene
        // create new scene
        Rectangle r = new Rectangle(15,15,150,670);
        r.setFill(Color.LIGHTGRAY);


        rootManager.getChildren().add(r);
        createPieChart(rootManager);
        createBtns(rootManager, stage);
        rootManager.getChildren().add(text);

        stage.setScene(Manager);
        stage.show();
    }


    private Popup createLogOutPopup(Stage popStage){
        Popup popup = new Popup();

        // Create VBox for popup
        VBox popupContent = new VBox(10);
        popupContent.setPadding(new Insets(10));
        popupContent.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 2px;");

        // Create the label for the confirmation message
        Label confirmationLabel = new Label("Log out or switch accounts");
        confirmationLabel.setFont(Font.font(14));

        // Create the Yes and No buttons
        Button yesBtn = new Button("Log Out");
        Button noBtn = new Button("Cashier");

        // Handle the Yes button (close the app or return to login screen)
        yesBtn.setOnAction(event -> {
            popStage.close(); // Close the application
            popup.hide();  // Hide the popup
        });

        // Handle the No button (just hide the popup)
        noBtn.setOnAction(event -> popup.hide());

        // Create a layout for buttons
        HBox buttonBox = new HBox(10, yesBtn, noBtn);
        buttonBox.setAlignment(Pos.CENTER);

        // Add the label and buttons to the VBox
        popupContent.getChildren().addAll(confirmationLabel, buttonBox);
        popupContent.setAlignment(Pos.CENTER);

        // Add the content to the popup
        popup.getContent().add(popupContent);

        return popup;
    }

    private void createBtns (Group manager, Stage stage) {
        Rectangle rec1 = new Rectangle(80, 40);
        Rectangle rec2 = new Rectangle(80, 40);
        Rectangle rec3 = new Rectangle(80, 40);
        Rectangle rec4 = new Rectangle(80, 40);
        rec1.setFill(Color.MAROON);
        rec2.setFill(Color.DARKCYAN);
        rec3.setFill(Color.DARKCYAN);
        rec4.setFill(Color.DARKCYAN);


        rec3.setOnMouseEntered(event -> rec3.setFill(Color.DARKGRAY));
        rec3.setOnMouseExited(event -> rec3.setFill(Color.DARKCYAN));
        rec4.setOnMouseEntered(event -> rec4.setFill(Color.DARKGRAY));
        rec4.setOnMouseExited(event -> rec4.setFill(Color.DARKCYAN));


//        Button logOutBtn = btnTemplate(1,"_Log Out");
//        logOutBtn.setStyle("-fx-background-color: maroon;");
//        logOutBtn.setTextFill(Color.WHITE);
//        rec1.setOnMouseEntered(event -> {
//            rec1.setFill(Color.DARKGRAY);
//            logOutBtn.setStyle(
//                    "-fx-background-color: darkgray;" +
//                            "-fx-min-width: 80px;" +
//                            "-fx-min-height: 40px;"
//            );
//        });
//        logOutBtn.setOnMouseEntered(event -> {
//            rec1.setFill(Color.DARKGRAY);
//            logOutBtn.setStyle(
//                    "-fx-background-color: darkgray;" +
//                            "-fx-min-width: 80px;" +
//                            "-fx-min-height: 40px;"
//            );
//        });
//
//        rec1.setOnMouseExited(event -> {
//            rec1.setFill(Color.DARKCYAN);
//            logOutBtn.setStyle(
//                    "-fx-background-color: maroon;" +
//                            "-fx-min-width: 80px;" +
//                            "-fx-min-height: 40px;"
//            );
//        });
//        logOutBtn.setOnMouseExited(event -> {
//            rec1.setFill(Color.DARKCYAN);
//            logOutBtn.setStyle(
//                    "-fx-background-color: maroon;" +
//                    "-fx-min-width: 80px;" +
//                    "-fx-min-height: 40px;"
//            );
//        });

        StackPane stackPane = btnTemplate(1, 50, 55,"_Log Out", stage);

        StackPane stackPane2 = btnTemplate(2, 50, 155,"_Clock IN \n OUT", stage);

        StackPane stackPane3 = btnTemplate(2, 50, 195, "_Statistics", stage);

        StackPane stackPane4 = btnTemplate(2, 50, 255, "_Inventory", stage);

        manager.getChildren().addAll(stackPane,stackPane2,stackPane3,stackPane4);

    }
    // Button template to make button changes simple
    private StackPane btnTemplate(int num, int x, int y, String s, Stage stage){
        StackPane dummy = new StackPane();
        Rectangle rec2 = new Rectangle(80, 40);

        if (num == 2) {
            rec2.setFill(Color.DARKCYAN);
            Button Btn = new Button(s);
            Btn.setStyle("-fx-background-color: dark cyan;");
            Btn.setTextFill(Color.WHITE);
            rec2.setOnMouseEntered(event -> {
                rec2.setFill(Color.DARKGRAY);
                Btn.setStyle(
                        "-fx-background-color: darkgray;" +
                                "-fx-min-width: 80px;" +
                                "-fx-min-height: 40px;"
                );
            });
            Btn.setOnMouseEntered(event -> {
                rec2.setFill(Color.DARKGRAY);
                Btn.setStyle(
                        "-fx-background-color: darkgray;" +
                                "-fx-min-width: 80px;" +
                                "-fx-min-height: 40px;"
                );
            });

            rec2.setOnMouseExited(event -> {
                rec2.setFill(Color.DARKCYAN);
                Btn.setStyle(
                        "-fx-background-color: darkcyan;" +
                                "-fx-min-width: 80px;" +
                                "-fx-min-height: 40px;"
                );
            });
            Btn.setOnMouseExited(event -> {
                rec2.setFill(Color.DARKCYAN);
                Btn.setStyle(
                        "-fx-background-color: darkcyan;" +
                                "-fx-min-width: 80px;" +
                                "-fx-min-height: 40px;"
                );
            });
            StackPane stackPane = new StackPane();
            stackPane.setLayoutX(x);
            stackPane.setLayoutY(y);
            stackPane.getChildren().addAll(rec2, Btn);

            return stackPane;
        }
        if(num == 1){
            rec2.setFill(Color.MAROON);
            Button Btn = new Button(s);
            Btn.setStyle("-fx-background-color: maroon;");
            Btn.setTextFill(Color.WHITE);
            rec2.setOnMouseEntered(event -> {
                rec2.setFill(Color.DARKGRAY);
                Btn.setStyle(
                        "-fx-background-color: darkgray;" +
                                "-fx-min-width: 80px;" +
                                "-fx-min-height: 40px;"
                );
            });
            Btn.setOnMouseEntered(event -> {
                rec2.setFill(Color.DARKGRAY);
                Btn.setStyle(
                        "-fx-background-color: darkgray;" +
                                "-fx-min-width: 80px;" +
                                "-fx-min-height: 40px;"
                );
            });

            rec2.setOnMouseExited(event -> {
                rec2.setFill(Color.MAROON);
                Btn.setStyle(
                        "-fx-background-color: maroon;" +
                                "-fx-min-width: 80px;" +
                                "-fx-min-height: 40px;"
                );
            });
            Btn.setOnMouseExited(event -> {
                rec2.setFill(Color.MAROON);
                Btn.setStyle(
                        "-fx-background-color: maroon;" +
                                "-fx-min-width: 80px;" +
                                "-fx-min-height: 40px;"
                );
            });
            StackPane stackPane = new StackPane();
            stackPane.setLayoutX(x);
            stackPane.setLayoutY(y);
            stackPane.getChildren().addAll(rec2, Btn);
            Popup logOutPopup = createLogOutPopup(stage);
            Btn.setOnAction(event -> logOutPopup.show(stage));

            return stackPane;
        }
        return dummy;
    }

    private void createPieChart(Group root){

        // Pull data from repository tomorrow
        PieChart.Data slice1 = new PieChart.Data("Sweet and Sour Chicken", 25);
        PieChart.Data slice2 = new PieChart.Data("Kung Pao Chicken", 25);
        PieChart.Data slice3 = new PieChart.Data("Fried Rice", 25);
        PieChart.Data slice4 = new PieChart.Data("Dumplings", 25);
        PieChart pieChart = new PieChart();
        pieChart.getData().addAll(slice1, slice2, slice3, slice4);

        pieChart.setTranslateX(150);
        pieChart.setTranslateY(50);
        pieChart.setPrefSize(400,200);

        // Add the pie chart to the root group
        root.getChildren().add(pieChart);
    }


    public void initialize(String[] args) {
        launch(args);
    }
}
