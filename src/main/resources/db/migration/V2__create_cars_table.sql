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