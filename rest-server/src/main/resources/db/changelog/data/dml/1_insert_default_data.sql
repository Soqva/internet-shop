--liquibase formatted sql
--changeSet SemakovND:1

INSERT INTO users (username, password, first_name, last_name)
VALUES ('s0qva', 's0qva', 'Никита', 'Семаков'),
       ('misha', 'misha', 'Миша', 'Михайлов'),
       ('egor', 'egor', 'Егор', 'Егоров'),
       ('zenya', 'zenya', 'Женя', 'Женов');

INSERT INTO dictionary_role (name, short_name, description)
VALUES ('user', 'U', 'среднестатистический пользователь магазина'),
       ('admin', 'A', 'администратор магазина');

INSERT INTO user_role (user_id, role_id)
VALUES (1, 2),
       (2, 1),
       (3, 1),
       (4, 1);

INSERT INTO dictionary_order_status (name, short_name, description)
VALUES ('accepted', 'ACC', 'заказ принят администратором магазина'),
       ('waiting', 'WTNG', 'заказ ожидает проверки администратором магазина'),
       ('canceled', 'CNCL', 'заказ отменен администратором магазина');

INSERT INTO dictionary_supplier (name, short_name, description)
VALUES ('ООО FINDWAY', 'FINDWAY', 'поставщик молочных продуктов'),
       ('ООО Ешь Вкусно!', 'Ешь Вкусно!', 'поставщик мясных изделий');
