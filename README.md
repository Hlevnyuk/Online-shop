docker run --name Online-shop -p 5433:5432 -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=pass -e POSTGRES_DB=Online-shop -d postgres

CREATE TABLE products( id SERIAL PRIMARY KEY, name CHAR(50) NOT NULL, price numeric NOT NULL, description CHAR(200) NOT NULL, date date NOT NULL, author_name CHAR(100) NOT NULL );

CREATE TABLE users( name CHAR(50) NOT NULL, email CHAR(50) NOT NULL, password CHAR(50) NOT NULL, sole CHAR(50) NOT NULL );
