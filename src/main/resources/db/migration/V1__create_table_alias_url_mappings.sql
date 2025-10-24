CREATE TABLE alias_with_url_mapping (
    alias VARCHAR(160) PRIMARY KEY,
    full_url VARCHAR(2048),
    short_url VARCHAR(255)
);