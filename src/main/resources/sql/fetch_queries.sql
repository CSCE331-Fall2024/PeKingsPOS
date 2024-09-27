-- get_menu_items
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

-- add_menu_item
INSERT INTO menu (name, price) VALUES ('%s', '%s');

-- get_menu_item
SELECT * FROM menu WHERE id = '%s';

-- get_ingredient
SELECT * FROM inventory WHERE id = '%s';

-- get_menu_item_ingredients
SELECT * FROM menu_ingredients WHERE id = '%s';

-- get_active_employees
SELECT * FROM employees WHERE is_clockedin = 'true';

-- get_all_employees
SELECT * FROM employees;

