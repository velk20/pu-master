-- users table
CREATE TABLE IF NOT EXISTS tb_users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(50) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    city VARCHAR(50) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    years INT NOT NULL,
    previous_accidents BOOLEAN NOT NULL,
    is_active INT DEFAULT 1,
    role VARCHAR(20) NOT NULL
);