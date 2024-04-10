DROP TABLE IF EXISTS user;

CREATE TABLE user(
    id INT PRIMARY KEY AUTO_INCREMENT,
    firstname VARCHAR(255),
    lastname VARCHAR(255),
    email VARCHAR(255),
    phone CHAR(12)
);

INSERT INTO user (firstname, lastname, email, phone)
VALUES
('John', 'Doe', 'john@example.com', 'password123'),
('Jane', 'Smith', 'jane@example.com', 'securepassword'),
('Alice', 'Johnson', 'alice@example.com', 'p@ssw0rd');
