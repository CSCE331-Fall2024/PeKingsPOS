package com.pekings.pos;


import javafx.application.Application;

//public class Main extends Application {
//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        primaryStage.setScene(new Scene(new CustomerController().getView()));
//        primaryStage.show();
//    }
//}



public class Main {
    public static void main(String[] args) throws Exception {
        POSApp posApp = new POSApp();
        posApp.initialize();

    }
}