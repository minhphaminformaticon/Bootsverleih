DROP TABLE IF EXISTS boat_management;

CREATE TABLE boat_management(
    id INT AUTO_INCREMENT PRIMARY KEY,
    type_of_boat VARCHAR(255),
    number_of_seats INT UNSIGNED,
    horse_power INT UNSIGNED,
    fk_boat_id INT,
    FOREIGN KEY (fk_boat_id) REFERENCES boat(id)
);

INSERT INTO boat_management (type_of_boat, number_of_seats, horse_power, fk_boat_id)
VALUES
('Speedboat', 4, 200, 1),
('Yacht', 8, 500, 2),
('Fishing Boat', 6, 150, 3);
