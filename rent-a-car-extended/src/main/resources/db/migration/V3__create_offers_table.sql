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