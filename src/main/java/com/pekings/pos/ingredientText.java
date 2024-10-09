package com.pekings.pos;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class ingredientText {
    boolean selected = false;
    String ingr;
    Manager manager;
    static List<String> newIngrList = new ArrayList<>();
    Text newWords;

    public ingredientText(String ingr, Manager manager, List<String> newIngrList){
        this.ingr = ingr;
        this.manager = manager;

        newWords = new Text();
        newWords.setText(ingr);
        newWords.setOnMouseClicked(_ -> {
            if(!selected) {
                newWords.setFill(Color.BLUE);
                newIngrList.add(newWords.getText());
                selected = !selected;
            }else{
                newWords.setFill(Color.BLACK);
                newIngrList.remove(newWords.getText());
                selected = !selected;
            }
        });
    }

    public Text getText(){
        return newWords;
    }

    public List<String> getList(){
        return newIngrList;
    }
}
