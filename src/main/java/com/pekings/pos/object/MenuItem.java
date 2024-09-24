package com.pekings.pos.object;

import java.util.List;

public class MenuItem {

    private final long id;
    private final List<Ingredient> ingredients;

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public long getId() {
        return id;
    }

    public MenuItem(long id, List<Ingredient> ingredients) {
        this.id = id;
        this.ingredients = ingredients;
    }
}
