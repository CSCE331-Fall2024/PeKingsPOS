-- get ALL menu items in one query
SELECT
    menu.id AS menu_item_id,
    menu.name AS menu_item_name,
    menu.price AS menu_item_price,
    inventory.name AS ingredient_name,
    menu_ingredients.ingredients_in_item AS ingredient_quantity,
    inventory.price_batch AS ingredient_batch_price,
    inventory.serving_price AS serving_price,
    inventory.id AS ingredient_id
FROM
    menu
        JOIN
    menu_ingredients ON menu.id = menu_ingredients.menu_item
        JOIN
    inventory ON menu_ingredients.ingredient_id = inventory.id;
