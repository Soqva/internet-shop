CREATE TABLE users
(
    id        BIGSERIAL PRIMARY KEY,
    username  VARCHAR(128) NOT NULL UNIQUE,
    firstname VARCHAR(32),
    lastname  VARCHAR(32),
    role      VARCHAR(32)  NOT NULL,
    banned    BOOLEAN      NOT NULL
);

CREATE TABLE product
(
    id    BIGSERIAL PRIMARY KEY,
    name  VARCHAR(128)     NOT NULL,
    price DOUBLE PRECISION NOT NULL
);

CREATE TABLE product_details
(
    id          BIGSERIAL PRIMARY KEY,
    product_id  BIGINT NOT NULL UNIQUE REFERENCES product (id) ON DELETE CASCADE ON UPDATE CASCADE,
    description VARCHAR(512),
    made_in     VARCHAR(64)
);

CREATE TABLE orders
(
    id         BIGSERIAL PRIMARY KEY,
    user_id    BIGINT REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
    order_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    status     VARCHAR(32)                 NOT NULL
);

CREATE TABLE orders_product
(
    id         BIGSERIAL PRIMARY KEY,
    order_id   BIGINT NOT NULL REFERENCES orders (id) ON DELETE CASCADE ON UPDATE CASCADE,
    product_id BIGINT NOT NULL REFERENCES product (id)
);
