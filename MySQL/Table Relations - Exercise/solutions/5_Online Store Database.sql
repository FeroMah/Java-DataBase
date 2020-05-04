CREATE TABLE cities
(
    city_id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name    VARCHAR(50)
);
CREATE TABLE customers
(
    customer_id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(50),
    birthday    DATE,
    city_id     INT(11),
    CONSTRAINT fk_city_id FOREIGN KEY (city_id) REFERENCES cities (city_id)
);

CREATE TABLE orders
(
    order_id    INT(11) AUTO_INCREMENT NOT NULL PRIMARY KEY,
    customer_id INT(11),
    CONSTRAINT fk_customer_id FOREIGN KEY (customer_id) REFERENCES customers (customer_id)
);
CREATE TABLE item_types
(
    item_type_id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name         VARCHAR(50)
);

CREATE TABLE items
(
    item_id      INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name         VARCHAR(50),
    item_type_id INT(11),
    CONSTRAINT fk_item_type_id FOREIGN KEY (item_type_id) REFERENCES item_types (item_type_id)
);

CREATE TABLE order_items
(
    order_id INT(11),
    item_id  INT(11),
    CONSTRAINT pk_order PRIMARY KEY (order_id, item_id),
    CONSTRAINT fk_oder_id FOREIGN KEY (order_id) REFERENCES orders (order_id),
    CONSTRAINT fk_item_id FOREIGN KEY (item_id) REFERENCES items (item_id)
);

