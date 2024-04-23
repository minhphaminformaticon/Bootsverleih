DROP TABLE IF EXISTS user;

CREATE TABLE user(
    id INT PRIMARY KEY AUTO_INCREMENT,
    firstname VARCHAR(255),
    lastname VARCHAR(255),
    email VARCHAR(255),
    phone CHAR(12)
);

