
-- inserting 1000 customers
INSERT INTO customers (id)
SELECT nextval(pg_get_serial_sequence('customers', 'id'))
FROM generate_series(1, 1000);

--- menu table is just:
-- INSERT INTO menu (name,price) VALUES ('example', 420.69)