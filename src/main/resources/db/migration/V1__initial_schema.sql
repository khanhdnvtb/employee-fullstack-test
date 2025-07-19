CREATE TABLE product
(
    id           BIGSERIAL PRIMARY KEY,
    title        VARCHAR(255) NOT NULL,
    handle       VARCHAR(255),
    vendor       VARCHAR(255),
    product_type VARCHAR(100)
);

CREATE TABLE variant
(
    id                BIGSERIAL PRIMARY KEY,
    product_id        BIGINT NOT NULL REFERENCES product (id),
    title             VARCHAR(255),
    option1           VARCHAR(100),
    option2           VARCHAR(100),
    option3           VARCHAR(100),
    sku               VARCHAR(100),
    requires_shipping BOOLEAN,
    taxable           BOOLEAN,
    available         BOOLEAN,
    price             NUMERIC(10, 2),
    compare_at_price  NUMERIC(10, 2),
    grams             INTEGER
);

CREATE TABLE image
(
    id         BIGSERIAL PRIMARY KEY,
    product_id BIGINT NOT NULL REFERENCES product (id),
    src        TEXT
);