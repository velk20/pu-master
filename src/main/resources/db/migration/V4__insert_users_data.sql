-- dummy users data
INSERT INTO tb_users (username, password, first_name, last_name, city, phone, years, previous_accidents, is_active, role)
VALUES ('admin','admin','John', 'Doe', 'Sofia', '1234567890', 30, FALSE, 1, 'Admin');

INSERT INTO tb_users (username, password, first_name, last_name, city, phone, years, previous_accidents, is_active, role)
VALUES ('admin1','admin1','Jane', 'Smith', 'Plovdiv', '0987654321', 25, TRUE, 1, 'User');

INSERT INTO tb_users (username, password, first_name, last_name, city, phone, years, previous_accidents, is_active, role)
VALUES ('admin2','admin2','Ivan', 'Ivanov', 'Varna', '1122334455', 40, FALSE, 1, 'User');

INSERT INTO tb_users (username, password, first_name, last_name, city, phone, years, previous_accidents, is_active, role)
VALUES ('admin3','admin3','Maria', 'Petrova', 'Burgas', '6677889900', 35, TRUE, 1, 'User');