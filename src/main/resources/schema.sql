--DROP TABLE IF EXISTS "customers";
--DROP TABLE IF EXISTS "employees";
--DROP TABLE IF EXISTS "orders";
--DROP TABLE IF EXISTS "menu";
--DROP TABLE IF EXISTS "inventory";
--DROP TABLE IF EXISTS "menu_ingredients";
--DROP TABLE IF EXISTS "individual_items";

CREATE TABLE "customers" ( --necessary?
    id SERIAL PRIMARY KEY
    --phone_num VARCHAR(15), --necessary?
    --email VARCHAR(50), --necessary?
    --last_purchase DATE --necessary?
    
);

CREATE TABLE "employees" (
    id SERIAL PRIMARY KEY,
    username TEXT,
    pass TEXT,
    position TEXT,
    last_clockin TIME, --timestamp
    is_clockedin BOOLEAN,
    total_hours INT 
);

CREATE TABLE "orders" (
    id SERIAL PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    price DECIMAL,
    payment_method TEXT,
    employee_id BIGINT NOT NULL,
    order_time TIME,
    CONSTRAINT fk_customer FOREIGN KEY (customer_id) REFERENCES customers(id),
    CONSTRAINT fk_employee FOREIGN KEY (employee_id) REFERENCES employees(id)
);

CREATE TABLE "menu" (
    id SERIAL PRIMARY KEY,
    name TEXT, -- adding names to menu items
    price DECIMAL
);

CREATE TABLE inventory (
    id SERIAL PRIMARY KEY,
    name TEXT,
    serving_price DECIMAL,
    amount INT,
    price_batch DECIMAL
    -- received DATE,
    -- expires DATE
);

INSERT INTO inventory (name, serving_price, amount, price_batch) VALUES ('Carrots', 1.0, 1000, 500.0);

-- List of ingredients for each menu item
-- This is how we know how many of what to remove from the inventory
-- Inventory removal is done in Java code. There are no SQL functions in the DB
CREATE TABLE "menu_ingredients" (
    id SERIAL PRIMARY KEY,
    ingredient_id INT,
    menu_item INT,
    ingredients_in_item INT,
    CONSTRAINT fk_item FOREIGN KEY (menu_item) REFERENCES menu(id),
    CONSTRAINT fk_ingredient FOREIGN KEY (ingredient_id) REFERENCES inventory(id)
);

-- Record of items purchased by customers
-- Different from menu_items. This is an individual sale of an item
-- An order has many individual items
CREATE TABLE "order_items" ( 
    id SERIAL PRIMARY KEY,
    order_id INT,
    item_id INT,
    CONSTRAINT fk_order FOREIGN KEY (order_id) REFERENCES orders(id),
    CONSTRAINT fk_item FOREIGN KEY (item_id) REFERENCES menu(id)
);
---INSERT INTO employees (username,pass,position,last_clockin,is_clockedin,total_hours) VALUES ('ThomasC', 'CC137', 'employee',00:00)