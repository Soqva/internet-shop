--liquibase formatted sql
--changeSet SemakovND:1

CREATE TABLE users
(
    id         BIGSERIAL PRIMARY KEY,
    username   VARCHAR(64)  NOT NULL UNIQUE,
    password   VARCHAR(512) NOT NULL,
    first_name VARCHAR(64),
    last_name  VARCHAR(64),
    blocked    BOOLEAN DEFAULT FALSE
);

CREATE TABLE dictionary_role
(
    name        VARCHAR(32) PRIMARY KEY,
    short_name  VARCHAR(16),
    description VARCHAR(128)
);

CREATE TABLE user_role
(
    id        BIGSERIAL PRIMARY KEY,
    user_name VARCHAR(64) NOT NULL REFERENCES users (username),
    role_name VARCHAR(32) DEFAULT 'user' REFERENCES dictionary_role (name),
    UNIQUE (user_name, role_name)
);

CREATE TABLE dictionary_order_status
(
    name        VARCHAR(32) PRIMARY KEY,
    short_name  VARCHAR(16),
    description VARCHAR(128)
);

CREATE TABLE orders
(
    id                BIGSERIAL PRIMARY KEY,
    order_status_name VARCHAR(32) NOT NULL REFERENCES dictionary_order_status (name),
    order_cost        DECIMAL     NOT NULL
);

CREATE TABLE user_order
(
    id            BIGSERIAL PRIMARY KEY,
    user_username VARCHAR(64) NOT NULL REFERENCES users (username),
    order_id      BIGINT      NOT NULL REFERENCES orders (id),
    UNIQUE (user_username, order_id)
);

CREATE TABLE dictionary_country
(
    name        VARCHAR(64) PRIMARY KEY,
    short_name  VARCHAR(32),
    description VARCHAR(128)
);

CREATE TABLE commodity
(
    id                     BIGSERIAL PRIMARY KEY,
    name                   VARCHAR(128) NOT NULL,
    cost                   DECIMAL      NOT NULL,
    available_amount       INTEGER      NOT NULL,
    description            VARCHAR(512),
    producing_name_country VARCHAR(64)  NOT NULL REFERENCES dictionary_country (name)
);

CREATE TABLE order_commodity
(
    id                         BIGSERIAL PRIMARY KEY,
    order_id                   BIGINT  NOT NULL REFERENCES orders (id),
    commodity_id               BIGINT  NOT NULL REFERENCES commodity (id),
    amount_of_bought_commodity INTEGER NOT NULL,
    UNIQUE (order_id, commodity_id)
);

CREATE TABLE sold_commodity
(
    id                         BIGSERIAL PRIMARY KEY,
    commodity_id               BIGINT  NOT NULL REFERENCES commodity (id),
    amount_of_sold_commodities INTEGER NOT NULL,
    sold_date                  BIGINT  NOT NULL
);

CREATE TABLE dictionary_supplier
(
    name        VARCHAR(64) PRIMARY KEY,
    short_name  VARCHAR(32),
    description VARCHAR(128)
);

CREATE TABLE supply
(
    id             BIGSERIAL PRIMARY KEY,
    supplier_name  VARCHAR(64) NOT NULL REFERENCES dictionary_supplier (name),
    receiving_date BIGINT      NOT NULL
);

CREATE TABLE supply_commodity
(
    id                             BIGSERIAL PRIMARY KEY,
    supply_id                      BIGINT  NOT NULL REFERENCES supply (id),
    commodity_id                   BIGINT  NOT NULL REFERENCES commodity (id),
    amount_of_supplied_commodities INTEGER NOT NULL
);

CREATE TABLE income_statement
(
    id                         BIGSERIAL PRIMARY KEY,
    amount_of_sold_commodities INTEGER NOT NULL,
    income_amount              DECIMAL NOT NULL,
    report_date                BIGINT  NOT NULL
);
