CREATE TABLE book
(
    id           int8         NOT NULL,
    created_date timestamp NULL,
    updated_date timestamp NULL,
    book_uuid    varchar(255) NOT NULL,
    description  varchar(255) NULL,
    name         varchar(255) NOT NULL,
    price        numeric(19, 2) NULL,
    stock        int4 NULL,
    CONSTRAINT book_pkey PRIMARY KEY (id),
    CONSTRAINT uk_md6i5l4ka1ds7ync1hq5fjrnk UNIQUE (book_uuid),
    CONSTRAINT uk_wugryet8mf6oi28n00x2eoc4 UNIQUE (name)
);

CREATE TABLE customer
(
    id                 int8         NOT NULL,
    created_date       timestamp NULL,
    updated_date       timestamp NULL,
    address            varchar(255) NOT NULL,
    customer_id        varchar(255) NOT NULL,
    email              varchar(150) NOT NULL,
    encrypted_password varchar(255) NOT NULL,
    last_name          varchar(150) NOT NULL,
    "name"             varchar(100) NOT NULL,
    CONSTRAINT customer_pkey PRIMARY KEY (id)
);

CREATE TABLE orders
(
    id           int8 NOT NULL,
    created_date timestamp NULL,
    updated_date timestamp NULL,
    price        numeric(19, 2) NULL,
    quantity     int4 NULL,
    status       varchar(255) NULL,
    "version"    int4 NOT NULL,
    book_id      int8 NULL,
    customer_id  int8 NULL,
    CONSTRAINT orders_pkey PRIMARY KEY (id)
);

ALTER TABLE orders
    ADD CONSTRAINT fk624gtjin3po807j3vix093tlf FOREIGN KEY (customer_id) REFERENCES customer (id);
ALTER TABLE orders
    ADD CONSTRAINT fkgfhb3qthqm0ds4n4o0yrgy9mj FOREIGN KEY (book_id) REFERENCES book (id);