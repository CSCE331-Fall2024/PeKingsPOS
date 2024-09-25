
-- inserting 1000 customers
INSERT INTO customers (id)
SELECT nextval(pg_get_serial_sequence('customers', 'id'))
FROM generate_series(1, 1000);

