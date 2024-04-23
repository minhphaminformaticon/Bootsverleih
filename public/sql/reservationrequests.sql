DROP TABLE IF EXISTS reservationrequests;

CREATE TABLE  reservationrequests(
    id INT AUTO_INCREMENT PRIMARY KEY,
    timefrom TIME NOT NULL,
    timeto TIME NOT NULL,
    reservationdate DATE NOT NULL,
    fk_boat_id INT NOT NULL,
    fk_customer_id INT,
    FOREIGN KEY (fk_boat_id) REFERENCES boat(id)
);


