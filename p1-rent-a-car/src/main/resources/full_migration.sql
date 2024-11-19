-- users table
CREATE TABLE IF NOT EXISTS tb_users (
                          id INT PRIMARY KEY AUTO_INCREMENT,
                          first_name VARCHAR(50) NOT NULL,
                          last_name VARCHAR(50) NOT NULL,
                          city VARCHAR(50) NOT NULL,
                          phone VARCHAR(20) NOT NULL,
                          years INT NOT NULL,
                          previous_accidents BOOLEAN NOT NULL,
                          is_active INT DEFAULT 1
);

-- dummy users data
INSERT INTO tb_users (first_name, last_name, city, phone, years, previous_accidents, is_active)
VALUES ('John', 'Doe', 'Sofia', '1234567890', 30, FALSE, 1);

INSERT INTO tb_users (first_name, last_name, city, phone, years, previous_accidents, is_active)
VALUES ('Jane', 'Smith', 'Plovdiv', '0987654321', 25, TRUE, 1);

INSERT INTO tb_users (first_name, last_name, city, phone, years, previous_accidents, is_active)
VALUES ('Ivan', 'Ivanov', 'Varna', '1122334455', 40, FALSE, 1);

INSERT INTO tb_users (first_name, last_name, city, phone, years, previous_accidents, is_active)
VALUES ('Maria', 'Petrova', 'Burgas', '6677889900', 35, TRUE, 1);

-- cars table
CREATE TABLE IF NOT EXISTS tb_cars (
                         id INT PRIMARY KEY AUTO_INCREMENT,
                         brand VARCHAR(50) NOT NULL,
                         model VARCHAR(50) NOT NULL,
                         create_year INT NOT NULL,
                         city VARCHAR(50) NOT NULL,
                         price_per_day NUMBER NOT NULL,
                         is_active INT DEFAULT 1
);

-- dummy cars data
INSERT INTO tb_cars (brand, model, create_year, city, price_per_day, is_active)
VALUES ('Toyota', 'Corolla', 2015, 'Sofia', 50.0, 1);

INSERT INTO tb_cars (brand, model, create_year, city, price_per_day, is_active)
VALUES ('Ford', 'Focus', 2018, 'Plovdiv', 55.0, 1);

INSERT INTO tb_cars (brand, model, create_year, city, price_per_day, is_active)
VALUES ('BMW', '3 Series', 2020, 'Varna', 80.0, 1);

INSERT INTO tb_cars (brand, model, create_year, city, price_per_day, is_active)
VALUES ('Audi', 'A4', 2019, 'Burgas', 75.0, 1);

-- offers table
CREATE TABLE IF NOT EXISTS tb_offers (
                           id INT PRIMARY KEY AUTO_INCREMENT,
                           car_id INT NOT NULL,
                           user_id INT NOT NULL,
                           price DOUBLE NOT NULL,
                           additional_price DOUBLE NOT NULL,
                           total_price DOUBLE NOT NULL,
                           start_date DATE NOT NULL,
                           end_date DATE NOT NULL,
                           is_active INT DEFAULT 1,
                            is_accepted BOOLEAN DEFAULT FALSE,
                           FOREIGN KEY (car_id) REFERENCES tb_cars(id),
                           FOREIGN KEY (user_id) REFERENCES tb_users(id)
);

-- dummy offers data
INSERT INTO tb_offers (car_id, user_id, price, additional_price, total_price, start_date, end_date, is_active, is_accepted)
VALUES (1, 1, 100.0, 20.0, 120.0, '2023-11-01', '2023-11-10', 1, FALSE);

INSERT INTO tb_offers (car_id, user_id, price, additional_price, total_price, start_date, end_date, is_active, is_accepted)
VALUES (2, 2, 200.0, 30.0, 230.0, '2023-11-05', '2023-11-12', 1, FALSE);

INSERT INTO tb_offers (car_id, user_id, price, additional_price, total_price, start_date, end_date, is_active, is_accepted)
VALUES (3, 1, 150.0, 15.0, 165.0, '2023-12-01', '2023-12-05', 1, FALSE);

INSERT INTO tb_offers (car_id, user_id, price, additional_price, total_price, start_date, end_date, is_active, is_accepted)
VALUES (4, 3, 250.0, 25.0, 275.0, '2024-01-10', '2024-01-15', 1, FALSE);



DROP TABLE tb_users;
DROP TABLE tb_cars;
DROP TABLE tb_offers;