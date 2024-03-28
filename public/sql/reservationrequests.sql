DROP TABLE IF EXISTS reservationrequests;

CREATE TABLE  reservationrequests(
    id INT AUTO_INCREMENT PRIMARY KEY,
    timefrom TIME NOT NULL,
    timeto TIME NOT NULL,
    reservationdate DATE NOT NULL,
    fk_boat_id INT NOT NULL,
    fk_customer_id INT NOT NULL,
    FOREIGN KEY (fk_boat_id) REFERENCES boat(id),
    FOREIGN KEY (fk_customer_id) REFERENCES user(id)
);

INSERT INTO boat_management (type_of_boat, number_of_seats, horse_power, fk_boat_id)
VALUES
('Speedboat', 4, 200, 1),
('Yacht', 8, 500, 2),
('Fishing Boat', 6, 150, 3);
