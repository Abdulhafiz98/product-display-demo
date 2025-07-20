CREATE TABLE product (
    id           BIGINT PRIMARY KEY,
    title        VARCHAR(200),
    vendor       VARCHAR(200),
    product_type VARCHAR(200),
    published_at TIMESTAMP,
    created_at   TIMESTAMP,
    updated_at   TIMESTAMP
);

CREATE TABLE variant (
    id                BIGINT PRIMARY KEY,
    title             VARCHAR(200),
    option1           VARCHAR(200),
    option2           VARCHAR(200),
    option3           VARCHAR(200),
    taxable           BOOLEAN,
    price             VARCHAR(50),
    grams             INT,
    created_at        TIMESTAMP,
    updated_at        TIMESTAMP,
    product_id        BIGINT NOT NULL REFERENCES product (id) ON DELETE CASCADE
);