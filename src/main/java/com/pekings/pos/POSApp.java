package com.pekings.pos;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class POSApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(new Controller().getView()));
        primaryStage.show();
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

    public void initialize() {
        launch();
    }
}
