CREATE TABLE IF NOT EXISTS user (
                                    id INT AUTO_INCREMENT PRIMARY KEY,
                                    first_name VARCHAR(255),
                                    last_name VARCHAR(255),
                                    email VARCHAR(255),
                                    nickname VARCHAR(255),
                                    password VARCHAR(255),
                                    role ENUM('USER', 'ADMIN') -- Используем ENUM для роли
);
