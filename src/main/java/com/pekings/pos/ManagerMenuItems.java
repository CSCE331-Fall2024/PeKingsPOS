//package com.pekings.pos;
//
//import com.pekings.pos.object.MenuItem;
//import com.pekings.pos.object.Order;
//import javafx.geometry.Insets;
//import javafx.scene.control.Button;
//import javafx.scene.layout.TilePane;
//import javafx.scene.layout.VBox;
//import javafx.scene.paint.Color;
//import javafx.scene.text.Text;
//import javafx.scene.text.TextFlow;
//
//import static javafx.scene.text.TextAlignment.CENTER;
//
//public class ManagerMenuItems {
//    MenuItem item;
//    TilePane pane;
//    Manager mgr;
//    boolean clicked = false;
//
//    public ManagerMenuItems(MenuItem item, TilePane pane, Manager mgr){
//        this.item = item;
//        this.pane = pane;
//        this.mgr = mgr;
//    }
//
//    public Button editMenuBtn(){
//
//        Button btn = new Button(item.getName());
//        btn.setTextAlignment(CENTER);
//        btn.setPrefWidth(100);
//        btn.setPrefHeight(100);
//        btn.setStyle("-fx-background-color: #BA6433");
//
//        btn.setOnAction(e -> {
//            mgr.edited = true;
//            TextFlow textHolder = new TextFlow();
//            Text txt = new Text(item.getName());
//            textHolder.getChildren().add(txt);
//            textHolder.setPrefWidth(150);
//            pane.getChildren().add(textHolder);
//
//            cashier.updateTotals(item.getPrice());
//
//            String price = String.valueOf(item.getPrice());
//            int size = price.length();
//            if(price.charAt(size - 1) == '.'){
//                price += "00";
//            }else if(price.charAt(size - 2) == '.'){
//                price += "0";
//            }
//            price = "$" + price;
//            Text priceTxt = new Text(price);
//            pane.getChildren().add(priceTxt);
//
////            row.getChildren().addAll(textHolder, priceTxt);
//
//            cashier.orderItems.add(item);
//
//            txt.setOnMouseClicked(m ->
//            {
//                if(!clicked) {
//                    txt.setFill(Color.DARKBLUE);
//                    priceTxt.setFill(Color.DARKBLUE);
////                    row.setStyle("-fx-background-color: BLUE");
//
//                    cashier.deleteTextHolder.add(textHolder);
//                    cashier.deleteText.add(priceTxt);
//
//                    cashier.deleteOrderItems.add(item);
//                }else{
//                    txt.setFill(Color.BLACK);
//                    priceTxt.setFill(Color.BLACK);
//
//                    cashier.deleteTextHolder.remove(textHolder);
//                    cashier.deleteText.remove(priceTxt);
//
//                    cashier.deleteOrderItems.remove(item);
//                }
//                clicked = !clicked;
//            });
//        });
//
//        return btn;
//    }
//
//}
//
//
//public class ManagerMenuItems{
//    MenuItem item;
//    TilePane pane;
//    Manager mgr;
//    private int quantity;
//    private double price;
//    private String name;
//
//    // Constructor for MMI class:
//    public ManagerMenuItems(String name, int quantity, double price){
//        this.name = name;
//        this.quantity = quantity;
//        this.price = price;
//    }
//
//    //Get and set functions
//    public String getItemName(){ return name; }
//    public void setItemName(String name){ this.name = name; }
//    public int getQuantity(){ return quantity; }
//    public void setQuantity(int quantity) {this.quantity = quantity; }
//    public double getPrice() { return price; }
//    public void setPrice(double price) { this.price = price; }
//
//
//}
