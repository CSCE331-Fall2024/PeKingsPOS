package com.pekings.pos.object;

import java.util.ArrayList;
import java.util.List;

public class MenuItem {

    private final long id;
    private final List<Ingredient> ingredients;
    private final String name;
    private final float price;
    private boolean active;

    public MenuItem(long id, String name, float price, List<Ingredient> ingredients, boolean active) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.ingredients = ingredients;
        this.active = active;
    }

    public List<Ingredient> getIngredients() {
        return new ArrayList<>(ingredients);
    }

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
