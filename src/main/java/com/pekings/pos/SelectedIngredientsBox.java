package com.pekings.pos;

import com.pekings.pos.object.Ingredient;
import javafx.scene.control.CheckBox;

import java.util.HashMap;
import java.util.Map;

public class SelectedIngredientsBox {
    private final Ingredient ingredient;
    private final Manager manager;
    private final CheckBox checkBox;

    public SelectedIngredientsBox(Ingredient ingredient, Manager manager) {
        this.ingredient = ingredient;
        this.manager = manager;

        checkBox = new CheckBox(ingredient.getName());
        checkBox.setSelected(manager.checkBoxStates.getOrDefault(ingredient, false));
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public Manager getManager() {
        return manager;
    }
}
