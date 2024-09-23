--DROP TABLE IF EXISTS "customers";
--DROP TABLE IF EXISTS "employees";
--DROP TABLE IF EXISTS "orders";
--DROP TABLE IF EXISTS "menu";
--DROP TABLE IF EXISTS "inventory";
--DROP TABLE IF EXISTS "menu_ingredients";
--DROP TABLE IF EXISTS "individual_items";

CREATE TABLE "customers" (
    id SERIAL PRIMARY KEY,
    phone_num VARCHAR(15),
    email VARCHAR(50),
    last_purchase DATE
);

CREATE TABLE "employees" (
    id SERIAL PRIMARY KEY,
    username VARCHAR(20),
    password VARCHAR(20),
    last_clockin DATE,
    is_clockedin BOOLEAN,
    total_hours INT
);

CREATE TABLE "orders" (
    id SERIAL PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    price INT,
    payment_method VARCHAR(10),
    employee_id BIGINT NOT NULL,
    order_time DATE,
    item VARCHAR(50),
    CONSTRAINT fk_customer FOREIGN KEY (customer_id) REFERENCES customers(id),
    CONSTRAINT fk_employee FOREIGN KEY (employee_id) REFERENCES employees(id)
);

CREATE TABLE "menu" (
    id SERIAL PRIMARY KEY,
    price INT
);

CREATE TABLE inventory (
    id SERIAL PRIMARY KEY,
    name VARCHAR(15),
    price INT,
    amount INT,
    price_batch INT,
    received DATE,
    expires DATE
);

-- List of ingredients for each menu item
-- This is how we know how many of what to remove from the inventory
-- Inventory removal is done in Java code. There are no SQL functions in the DB
CREATE TABLE "menu_ingredients" (
    id SERIAL PRIMARY KEY,
    ingredient_id BIGINT NOT NULL,
    menu_item BIGINT NOT NULL,
    amount INT,
    CONSTRAINT fk_item FOREIGN KEY (menu_item) REFERENCES menu(id),
    CONSTRAINT fk_ingredient FOREIGN KEY (ingredient_id) REFERENCES inventory(id)
);

-- Record of items purchased by customers
-- Different from menu_items. This is an individual sale of an item
-- An order has many individual items
CREATE TABLE "individual_items" (
    id SERIAL PRIMARY KEY,
    order_id BIGINT,
    item_id BIGINT,
    CONSTRAINT fk_order FOREIGN KEY (order_id) REFERENCES orders(id),
    CONSTRAINT fk_item FOREIGN KEY (item_id) REFERENCES menu(id)
);
