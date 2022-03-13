INSERT INTO users (username, password, firstname, lastname, role, banned)
VALUES ('s0qva', 's0qva', 'Nikita', 'Semakov', 'USER', FALSE),
       ('misha', 'misha', 'Misha', 'Mihailov', 'USER', FALSE),
       ('egor', 'egor', 'Egor', 'Egorov', 'ADMIN', FALSE),
       ('zenya', 'zenya', 'Zenya', 'Zenyov', 'USER', FALSE);

INSERT INTO product (name, price)
VALUES ('Apple', 10),
       ('Bread', 5),
       ('Juice', 11),
       ('Sugar', 50),
       ('Salt', 33),
       ('Salad', 41),
       ('Potato', 19);

INSERT INTO product_details (product_id, description, made_in)
VALUES (1, 'Description for the apple', 'RUSSIA'),
       (2, 'Bread desc', 'USA'),
       (3, 'Juice is a testy product', 'FRANCE'),
       (4, 'Sugar is god damn sweet!', 'CHINA'),
       (5, 'Vegans like salad I think', 'GB');


INSERT INTO orders (user_id, order_date, status)
VALUES (1, current_timestamp, 'ACCEPTED'),
       (1, current_timestamp, 'WAITING'),
       (2, current_timestamp, 'CANCELED'),
       (3, current_timestamp, 'ACCEPTED'),
       (4, current_timestamp, 'WAITING');

INSERT INTO orders_product (order_id, product_id)
VALUES (1, 1),
       (1, 2),
       (1, 2),
       (2, 3),
       (2, 4),
       (3, 5),
       (3, 1),
       (4, 2),
       (5, 3);
