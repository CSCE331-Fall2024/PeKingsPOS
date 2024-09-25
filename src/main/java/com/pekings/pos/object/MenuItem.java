package com.pekings.pos.object;

import java.util.List;

public class MenuItem {

    private final long id;
    private final List<Ingredient> ingredients;
    private final String name;
    private final int price;

    public MenuItem(long id, String name, int price, List<Ingredient> ingredients) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.ingredients = ingredients;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
